package com.ind.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.EligibilityDeterminationTable;

public interface EligibilityDeterminationRepo extends JpaRepository<EligibilityDeterminationTable, Integer> {

}
