package com.ind.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.User;
import com.ind.entity.UserRole;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByUserEmail(String userEmail);

	public User findByUserEmailAndUserPszzd(String userEmail, String userPszzd);
	
	public List<User> findByUserRoleId(UserRole userRoleId);

}
