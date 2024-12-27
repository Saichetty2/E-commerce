package com.user.service;

import java.util.List;
import com.user.dto.UserDto;
import com.user.entity.User;


public interface UserService {
	
	UserDto saveUser(User user);
	
	UserDto getById(Long userId);
	
	List<UserDto> getAll ();
	
	User findByUserNameOrEmail(String userName, String email);
	
	

}
