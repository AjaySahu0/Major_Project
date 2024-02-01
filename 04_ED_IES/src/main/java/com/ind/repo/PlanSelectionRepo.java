package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.PlanSelection;

public interface PlanSelectionRepo extends JpaRepository<PlanSelection, Integer> {
	
	public PlanSelection findByCaseId(Integer caseId);
	
	//public List<PlanSelection> findByCaseId(Integer caseId);
	
	//public List<PlanSelection> findByCpNum(Integer cpNum);

}
