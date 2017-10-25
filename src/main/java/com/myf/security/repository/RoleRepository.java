package com.myf.security.repository;

import com.myf.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by myf on 2017/10/20.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
}
