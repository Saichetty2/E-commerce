package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	
	User findByUserNameOrEmail(String userName, String email);
	
	Optional<User> findByUserId(String userId);
	
//	User findByEmail(String email);
	
}
