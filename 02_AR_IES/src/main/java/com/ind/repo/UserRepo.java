package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByUserEmail(String userEmail);

	public User findByUserEmailAndUserPszzd(String userEmail, String userPszzd);

}
