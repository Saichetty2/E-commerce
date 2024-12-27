package com.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;

import jakarta.ws.rs.BeanParam;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    

    // Create a logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    
    @Override
    public UserDto saveUser(User user) {
        logger.info("Saving user: {}", user.getUserName());
        
        if (user.getPassword().equals(user.getPassword2())) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); 
        } else {
            logger.error("Passwords do not match for user: {}", user.getUserName());
            throw new IllegalArgumentException("Passwords do not match.");
        }
        
        user.setRole("ROLE_USER");
        
        User savedUser = userRepository.save(user);
        
        logger.info("User saved successfully: {}", savedUser.getUserName());
        logger.info("User first name: {}", savedUser.getFirstName());
        
        return convertToDto(savedUser);
    }


    
    
    @Override
    public UserDto getById(Long userId) {
        logger.info("Fetching user by ID: {}", userId);

        // Fetch the user by ID
        User userById = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found with ID: " + userId);
                });

        // Log the successful retrieval
        logger.info("User fetched successfully: {}", userById.getUserName());
        
        // Return the UserDto
        return convertToDto(userById);
    }

    @Override
    public List<UserDto> getAll() {
    	
        logger.info("Fetching all users.");

        List<User> allUsers = userRepository.findAll();
         
        
        logger.info("Fetched {} users.", allUsers.size());
        
        
        return allUsers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    
    
    private UserDto convertToDto(User user) {
        logger.debug("Converting User entity to UserDto: {}", user.getUserName());
        
        UserDto userDto = new UserDto();
        
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUserName());
        userDto.setRole(user.getRole());
        
        logger.debug("UserDto created: {}", userDto.getUserName());
        
        return userDto;
    }

	@Override
	public User findByUserNameOrEmail(String userName, String email) {
		
		return userRepository.findByUserNameOrEmail(userName, email);
	}
	


//	public User findByEmail(String email) {
//		User findByUserName = userRepository.findByEmail(email);
//		System.out.println("This is user service takes username from the feign client: "+ email);
//		System.out.println("This is user service takes username from the feign client: "+ findByUserName);
//		return findByUserName;
//	}

	
	

}
