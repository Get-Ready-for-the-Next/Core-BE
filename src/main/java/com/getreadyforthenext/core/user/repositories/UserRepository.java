package com.getreadyforthenext.core.user.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

}
