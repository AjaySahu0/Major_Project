package com.ind.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan, Integer> {
	
	List<Plan> findByPlanActiveStatus(String planActiveStatus);

}
