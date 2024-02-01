package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.IncomeDetails;

public interface IncomeDetailsRepo extends JpaRepository<IncomeDetails, Integer> {
	
	public IncomeDetails findByCaseId(Integer caseId);

}
