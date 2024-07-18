package com.getreadyforthenext.core.user.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

}
