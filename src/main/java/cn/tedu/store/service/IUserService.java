package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.bean.User;

public interface IUserService {
	
	int register(User user);
	
	List<User> findAll();
	
	User findUserById(Integer id);
	
	void updateUser(User user);
}
