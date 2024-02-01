package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan, Integer> {

}
