package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.user.entities.User;
import com.getreadyforthenext.core.user.enums.AuthenticationProvider;
import com.getreadyforthenext.core.user.enums.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository;

    public UserRepositorySupport(JPAQueryFactory jpaQueryFactory, UserRepository userRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.userRepository = userRepository;
    }

    public void googleUserRegistration(Map<String, Object> googleUserInfo) {
        String email = (String) googleUserInfo.get("email");

        if (userRepository.findByEmail(email).isPresent()) return;

        User user = new User();
        user.setEmail(email);
        user.setName((String) googleUserInfo.get("name"));
        user.setAuthenticationProvider(AuthenticationProvider.GOOGLE);
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}
