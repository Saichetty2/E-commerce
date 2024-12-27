package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.service.UserServiceImpl;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	

    @PostMapping("/save")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
    
    
    @GetMapping("/find/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }
    
    @GetMapping("/findall")
    public ResponseEntity<List<UserDto>> findAllUsers(){
    	return ResponseEntity.ok(userService.getAll());
    }
    
    
    @GetMapping("/get-ue/{userNameOrEmail}")
    public ResponseEntity<User> findByUserNameOrEmail(@PathVariable String userNameOrEmail){
    	User findByUserNameOrEmail = userService.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);
		return ResponseEntity.ok(findByUserNameOrEmail);
    	
    }
    
//    @GetMapping("/get-e/{email}")
//    public User findByEmail(@PathVariable String email) {
//    	return userService.findByEmail(email);
//    }
  
    
}
