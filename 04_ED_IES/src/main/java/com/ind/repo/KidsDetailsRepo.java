package com.ind.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.KidsDetails;

public interface KidsDetailsRepo extends JpaRepository<KidsDetails, Integer> {
	
	public List<KidsDetails> findByCaseId(Integer caseId);

}
