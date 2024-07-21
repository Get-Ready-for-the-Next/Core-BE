package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.common.utils.CommonUtil;
import com.getreadyforthenext.core.user.entities.User;
import com.getreadyforthenext.core.user.enums.AuthenticationProvider;
import com.getreadyforthenext.core.user.enums.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositorySupport {

    private final CommonUtil commonUtil;
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final PasswordEncoder passwordEncoder;

    public UserRepositorySupport(CommonUtil commonUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, JPAQueryFactory jpaQueryFactory) {
        this.commonUtil = commonUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void googleUserRegistration(Map<String, Object> googleUserInfo) {
        String email = (String) googleUserInfo.get("email");

        if (userRepository.findByEmail(email).isPresent()) return;

        User user = new User();
        user.setEmail(email);
        user.setName((String) googleUserInfo.get("name"));
        String encodedPassword = passwordEncoder.encode(commonUtil.generateRandomString(32));
        user.setPassword(encodedPassword);
        user.setAuthenticationProvider(AuthenticationProvider.GOOGLE);
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}
