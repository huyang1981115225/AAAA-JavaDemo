package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.bean.User;

public interface UserMapper {
	/**
	 * 插入
	 * @param user
	 */
	int insert(User user);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> findAll();
	
	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	User findUserById(Integer id);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(User user);
}
