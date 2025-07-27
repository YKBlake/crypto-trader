package com.ykb.app.cryptotrader.auth.service

import com.ykb.app.cryptotrader.data.auth.dao.UserDao
import com.ykb.app.cryptotrader.data.auth.model.Role
import com.ykb.app.cryptotrader.data.auth.model.User
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.log.LogMessage
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsPasswordService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class UserManager(
    private val dao: UserDao,
    private var users: MutableList<User> = dao.findAll().toMutableList(),
    private val passEncoder: PasswordEncoder,
    @Value("\${app.property.user.expireTime}") private val expireTime: Long,
    @Value("\${app.property.user.credentialsExpireTime}") private val credentialsExpireTime: Long
) : UserDetailsManager, UserDetailsPasswordService {

    private val logger: Log = LogFactory.getLog(javaClass)
    private val securityContextHolderStrategy: SecurityContextHolderStrategy =
        SecurityContextHolder.getContextHolderStrategy()
    private var authenticationManager: AuthenticationManager? = null

    fun setAuthenticationManager(authenticationManager: AuthenticationManager) {
        this.authenticationManager = authenticationManager
    }

    override fun loadUserByUsername(username: String?): User {
        val user = users.stream()
            .filter { it.username == username }
            .findFirst()
            .orElseThrow { UsernameNotFoundException(username) }
        return user
    }

    fun createUser(username: String, password: String, vararg roles: Role) {
        Assert.isTrue(username.isNotBlank(), "username param must be filled")
        Assert.isTrue(password.isNotBlank(), "password param must be filled")
        Assert.isTrue(roles.isNotEmpty(), "roles param must be filled")

        val mutableUser = createEmptyUser()
        mutableUser.username = username
        mutableUser.password = passEncoder.encode(password)
        mutableUser.setRoles(roles.toList())

        dao.save(mutableUser)
        users.add(mutableUser)
    }

    override fun createUser(user: UserDetails?) {
        Assert.isTrue(user!! !is User, "user param must be a User entity")
        Assert.isTrue(!userExists(user.username), "user should not exist")

        val mutableUser = createEmptyUser()
        if (user.password.isNotBlank())
            mutableUser.password = passEncoder.encode(mutableUser.password)
        dao.save(mutableUser)
        users.add(mutableUser)
    }

    override fun updateUser(user: UserDetails?) {
        Assert.isTrue(userExists(user!!.username), "user should exist")
        val mutableUser: User = loadUserByUsername(user.username)
        val index = users.indexOf(mutableUser)
        if (user.password.isNotBlank())
            mutableUser.password = passEncoder.encode(mutableUser.password)
        dao.save(mutableUser)
        users[index] = mutableUser
    }

    override fun deleteUser(username: String?) {
        val mutableUser: User = loadUserByUsername(username!!)
        dao.delete(mutableUser)
        users.remove(mutableUser)
    }

    override fun changePassword(oldPassword: String?, newPassword: String?) {
        if (oldPassword.isNullOrEmpty() || newPassword.isNullOrEmpty())
            throw RuntimeException("Password cannot be empty oldPassword($oldPassword), newPassword($newPassword)")
        val currentUser: Authentication = securityContextHolderStrategy.context.authentication
            ?: throw AccessDeniedException("Can't change password as no Authentication object found in context for current user.")
        val username = currentUser.name

        if (authenticationManager != null) {
            logger.debug(LogMessage.format("Reauthenticating user '%s' for password change request.", username))
            val authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, oldPassword)
            authenticationManager!!.authenticate(authentication)
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.")
        }

        logger.debug(LogMessage.format("Changing password for user '%s'", username))
        val user: User = loadUserByUsername(username)
        val index = users.indexOf(user)
        user.password = newPassword
        dao.save(user)
        users[index] = user
    }

    override fun userExists(username: String?): Boolean {
        return users.stream().anyMatch { it.username == username }
    }

    override fun updatePassword(user: UserDetails?, newPassword: String?): User {
        if (newPassword.isNullOrEmpty())
            throw RuntimeException("newPassword cannot be empty")
        val username = user!!.username
        val mutableUser: User = loadUserByUsername(username)
        mutableUser.password = newPassword
        val index = users.indexOf(mutableUser)
        dao.save(mutableUser)
        users[index] = mutableUser
        return mutableUser
    }

    fun loadFromDao() {
        users = dao.findAll().toMutableList()
    }

    fun clear() {
        users.clear()
        dao.deleteAll()
    }

    private fun createEmptyUser(): User {
        return User("", "", mutableListOf(), createExpireDate(), false, createCredentialsExpireDate(), true)
    }

    private fun createExpireDate(): Date? {
        return if (expireTime == -1L)
            null
        else
            Date(System.currentTimeMillis() + expireTime)
    }

    private fun createCredentialsExpireDate(): Date {
        return Date(System.currentTimeMillis() + credentialsExpireTime)
    }

    fun getUsers(): List<User> {
        return users
    }
}