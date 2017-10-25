package com.myf.security.repository;

import com.myf.security.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by myf on 2017/10/20.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
