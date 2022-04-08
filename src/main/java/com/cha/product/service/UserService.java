package com.cha.product.service;

import java.util.List;

import com.cha.product.model.User;

public interface UserService {

	User saveUser(User user);

	User updateUser(User user);

	void deleteUserById(Long id);

	User findUserByUserName(String userName);

	List<User> findAllUsers();

	Long nbOfUsers();

}
