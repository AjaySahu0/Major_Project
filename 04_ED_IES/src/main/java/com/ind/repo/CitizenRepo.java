package com.ind.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.CitizenAllData;
import com.ind.entity.User;

public interface CitizenRepo extends JpaRepository<com.ind.entity.CitizenAllData, Integer> {

	public List<CitizenAllData> findByUsercId(User useruId);

}
