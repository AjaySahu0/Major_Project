package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.EducationDetails;

public interface EducationDetailsRepo extends JpaRepository<EducationDetails, Integer> {

	public EducationDetails findByCaseId(Integer caseId);

}
