package com.getreadyforthenext.core.user.repositories;

import com.getreadyforthenext.core.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
