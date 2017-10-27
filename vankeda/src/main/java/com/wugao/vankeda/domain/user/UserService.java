package com.wugao.vankeda.domain.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.wugao.vankeda.infrastructure.exception.AppException;
import com.wugao.vankeda.infrastructure.spring.security.PasswordEncoder;

@Validated
@Service
public class UserService {

	public static final String USERNAME_ADMIN = "admin";

	public PasswordEncoder passwordEncoder = new PasswordEncoder();
	
	@Autowired
	private UserRepo userRepo;

	public User saveUser(@Valid User user) {
		// 用户名
		if (userRepo.getByUsername(user.getUsername()) != null) {
			throw new AppException("账号已存在");
		}
		// 密码
		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} 
		return userRepo.save(user);
	}

	public void updateUser(String id, @Valid User user) {
		User savedUser = userRepo.getById(id);
		if (savedUser == null) {
			throw new AppException("账号不存在");
		}
		// 用户名（不能更改）
		// 密码
		savedUser.setPassword(StringUtils.isEmpty(user.getPassword()) ? savedUser.getPassword() : user.getPassword());
		// 昵称
		savedUser.setNickname(StringUtils.isEmpty(user.getNickname()) ? savedUser.getNickname() : user.getNickname());
		// 手机号
		savedUser.setMobile(user.getMobile());
		savedUser.setEmail(user.getEmail());
		// 状态
		savedUser.setEnabled(user.getEnabled() == null ? savedUser.getEnabled() : user.getEnabled());
		//支付宝账号
		savedUser.setAlipayNo(user.getAlipayNo());
		// 保存更新
		userRepo.update(savedUser);
	}

}
