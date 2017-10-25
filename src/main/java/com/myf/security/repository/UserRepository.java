package com.myf.security.repository;

import com.myf.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by myf on 2017/10/20.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
}
