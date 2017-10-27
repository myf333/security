package com.myf.security;

import com.myf.security.model.Department;
import com.myf.security.model.Role;
import com.myf.security.model.User;
import com.myf.security.repository.DepartmentRepository;
import com.myf.security.repository.RoleRepository;
import com.myf.security.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	RoleRepository roleRepository;
//
//	@Test
//	public void testInit() {
//		userRepository.deleteAll();
//		departmentRepository.deleteAll();
//		roleRepository.deleteAll();
//
//		Department department = new Department();
//		department.setName("开发部");
//		departmentRepository.save(department);
//		Assert.assertNotNull(department.getId());
//
//		Role role = new Role();
//		role.setName("admin");
//		roleRepository.save(role);
//		Assert.assertNotNull(role.getId());
//
//		User user = new User();
//		user.setName("myf");
//		user.setCreate_date(new Date());
//		List<Role> roles = roleRepository.findAll();
//		user.setRoles(roles);
//		user.setDepartment(department);
//		userRepository.save(user);
//		Assert.assertNotNull(user.getId());
//	}
//
	@Test
	public void testDatabase(){
		Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
		Page<User> users = userRepository.findAll(pageable);
		Assert.assertNotNull(users);
	}

}
