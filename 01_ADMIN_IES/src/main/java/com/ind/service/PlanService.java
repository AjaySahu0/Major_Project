package com.ind.service;

import java.util.List;

import com.ind.binding.PlanForm;
import com.ind.entity.Plan;

public interface PlanService {

	public String create(Integer id, PlanForm data);
	
	public PlanForm edit (Integer planId);

	public boolean update(Integer id, Integer planId, PlanForm data);

	public List<Plan> getAllPlans();

	public String actionById(Integer id, Integer planId);

}
