package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.user.entities.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.getreadyforthenext.core.user.entities.QUser.user;


@Repository
public class UserRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    public void example() {
        List<User> users = jpaQueryFactory.selectFrom(user).fetch();

        for (User userdata : users) {
            this.jpaQueryFactory.update(user)
                    .set(user.name, "비회원")
                    .execute();
        }
    }

}
