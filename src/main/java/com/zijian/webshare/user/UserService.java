package com.zijian.webshare.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * 注册接口
     * @param username 用户名
     * @param password 未加密的密码
     * @return 用户未注册时返回未注册的用户，此时密码已经加密
     */
    public User register(String username, String password) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("用户已存在!");
        });

        String encryptedPassword = encoder.encode(password);
        return userRepository.save(new User(username, encryptedPassword));
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 返回Optional类，因为用户可能不存在
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 匹配用户密码
     * @param u1 登录用户
     * @param u2 数据库查出来的用户
     * @return
     */
    public boolean checkPassword(String raw, String encrypted) {
        return encoder.matches(raw, encrypted);
    }
}
