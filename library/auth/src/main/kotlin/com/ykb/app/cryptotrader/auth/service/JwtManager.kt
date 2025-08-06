package com.ykb.app.cryptotrader.auth.service

import com.ykb.app.cryptotrader.data.auth.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtManager(
    @Value($$"${app.property.jwt.refresh.secret}") jwtRefreshSecret: String,
    @Value($$"${app.property.jwt.access.secret}") jwtAccessSecret: String,
    @Value($$"${app.property.jwt.refresh.expireTime}") private val refreshExpireTime: Long,
    @Value($$"${app.property.jwt.access.expireTime}") private val accessExpireTime: Long,
    private val userManager: UserManager
) {

    private val algo = SignatureAlgorithm.HS512
    private val refreshKey = SecretKeySpec(jwtRefreshSecret.toByteArray(), algo.jcaName)
    private val accessKey = SecretKeySpec(jwtAccessSecret.toByteArray(), algo.jcaName)

    fun generateRefreshToken(user: User): String {
        return generateToken(user, refreshKey, refreshExpireTime)
    }

    fun generateAccessToken(user: User): String {
        return generateToken(user, accessKey, accessExpireTime)
    }

    private fun generateToken(user: User, key: SecretKeySpec, expireTime: Long): String {
        val claims: Map<String, Any> = HashMap()
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expireTime))
            .signWith(key, algo)
            .compact()
    }

    fun getUsernameFromRefreshToken(token: String): String? {
        val claims = getClaimsFromToken(token, refreshKey)
        return claims.subject
    }

    fun getUsernameFromAccessToken(token: String): String? {
        val claims = getClaimsFromToken(token, accessKey)
        return claims.subject
    }

    fun validateRefreshToken(token: String): Boolean {
        try {
            val username = getUsernameFromRefreshToken(token)
            userManager.loadUserByUsername(username)
            return !isTokenExpired(token, refreshKey)
        } catch(ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    fun validateAccessToken(token: String): Boolean {
        try {
            val username = getUsernameFromAccessToken(token)
            userManager.loadUserByUsername(username)
            return !isTokenExpired(token, accessKey)
        } catch(ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    private fun getClaimsFromToken(token: String, key: SecretKeySpec): Claims {
        val jwtParser: JwtParser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
        return jwtParser.parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String, key: SecretKeySpec): Boolean {
        val expiration = getClaimsFromToken(token, key).expiration
        return expiration.before(Date())
    }
}