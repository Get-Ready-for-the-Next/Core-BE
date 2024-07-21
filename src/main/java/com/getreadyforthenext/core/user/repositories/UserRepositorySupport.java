package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.common.utils.CommonUtil;
import com.getreadyforthenext.core.user.entities.User;
import com.getreadyforthenext.core.user.enums.AuthenticationProvider;
import com.getreadyforthenext.core.user.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositorySupport {

    private final CommonUtil commonUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRepositorySupport(CommonUtil commonUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.commonUtil = commonUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User googleUserRegistration(Map<String, Object> googleUserInfo) {
        String email = (String) googleUserInfo.get("email");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User user = new User();
        user.setEmail(email);
        user.setName((String) googleUserInfo.get("name"));
        String encodedPassword = passwordEncoder.encode(commonUtil.generateRandomString(32));
        user.setPassword(encodedPassword);
        user.setAuthenticationProvider(AuthenticationProvider.GOOGLE);
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}
