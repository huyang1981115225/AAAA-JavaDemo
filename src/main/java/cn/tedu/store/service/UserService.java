package cn.tedu.store.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.tedu.store.bean.User;
import cn.tedu.store.mapper.UserMapper;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	// 从db.properties文件中,获取盐,给salt赋值
	@Value("#{dbConfig.salt}")
	private String salt;

	public int register(User user) {
		/**
		 * md5加密
		 */
		String pwd = user.getPassword();

		// 生成md5密码
		String md5 = DigestUtils.md5Hex(pwd + salt);
		user.setPassword(md5);
		int result = userMapper.insert(user);
		System.out.println(user.getName()+"注册成功！");
		System.out.println("result:"+result);
		System.out.println("id:  "+user.getId());
		return result;
	}

	public List<User> findAll() {
		return userMapper.findAll();
	}

	public void updateUser(User user) {
		/**
		 * md5加密
		 */
		String pwd = user.getPassword();

		// 生成md5密码
		String md5 = DigestUtils.md5Hex(pwd + salt);
		user.setPassword(md5);;
		userMapper.updateUser(user);
	}

	public User findUserById(Integer id) {
		return userMapper.findUserById(id);
	}
}
