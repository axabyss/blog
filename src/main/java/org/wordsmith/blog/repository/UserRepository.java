package org.wordsmith.blog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.wordsmith.blog.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
/*	*/ /**
	 * 创建或修改用户
	 * @param user
	 * @return
	 *//*
	User saveOrUpdateUser(User user);
	
	*//**
	 * 删除用户 
	 * @param id
	 *//*
	void deleteUser(Long id);
	
	*//**
	 * 根据id查询用户
	 * @param id
	 * @return
	 *//*
	User getUserById(Long id);
	
	*//**
	 * 获取用户列表
	 * @return
	 *//*
	List<User> listUsers(); */
	 
}
