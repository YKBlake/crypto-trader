package com.ykb.app.cryptotrader.auth.service;

import com.ykb.app.cryptotrader.data.dao.UserDao;
import com.ykb.app.cryptotrader.data.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserOperationsService implements UserDetailsService, UserDetailsPasswordService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final List<User> users;

    public UserOperationsService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        users = userDao.findAll();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "Given username is blank");
        return users.stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() ->
                        new UsernameNotFoundException("There is no user with username: '" + username + "'")
                );
    }

    @Override
    public User updatePassword(UserDetails user, String newPassword) {
        Assert.hasText(newPassword, "Given password is blank");
        Assert.notNull(user, "UserDetails must not be null");
        Assert.isInstanceOf(User.class, user, "Class reference mismatch: " + user.getClass());

        int index = users.indexOf((User) user);
        if(index==-1)
            throw new UsernameNotFoundException("Given user not found");

        User cachedUser = users.get(index);
        cachedUser.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(cachedUser);
        return cachedUser;
    }

    public User create(String username, String password) {
        Assert.hasText(username, "Given username is blank");
        Assert.hasText(password, "Given password is blank");
        if(userDao.usernameExists(username))
            throw new IllegalArgumentException("There is already a user with username: " + username);

        User user = new User(
                username,
                password,
                null,
                null,
                null
        );
        users.add(user);
        userDao.save(user);
        return user;
    }

}
