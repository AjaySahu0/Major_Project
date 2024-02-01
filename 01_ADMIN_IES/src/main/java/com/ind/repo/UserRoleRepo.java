package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

}
