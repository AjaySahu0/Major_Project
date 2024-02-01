package com.ind.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ind.binding.PlanForm;
import com.ind.constants.AppConstants;
import com.ind.entity.Plan;
import com.ind.entity.User;
import com.ind.repo.PlanRepo;
import com.ind.repo.UserRepo;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	public String create(Integer id, PlanForm data) {

		Plan pEntity = new Plan();
		BeanUtils.copyProperties(data, pEntity);
		// pEntity.setPlanActiveStatus(AppConstants.STR_INACTIVE);

		System.out.println(id);

		Optional<User> optional = userRepo.findById(id);
		
		if (optional.isPresent()) {
			User user = optional.get();
			pEntity.setUseruId(user);
			pEntity.setCreatedBy(id);
			pEntity.setUpdatedBy(id);
			System.out.println(user);
			planRepo.save(pEntity); // upsert (insert / update based on id)
		}

		return AppConstants.PLAN_CREATE_SUCCESS_MSG;
	}

	@Override
	public PlanForm edit(Integer planId) {

		Optional<Plan> optional = planRepo.findById(planId);
		if (optional.isPresent()) {
			Plan plan = optional.get();

			PlanForm pf = new PlanForm();
			BeanUtils.copyProperties(plan, pf);
			return pf;
		}

		return null;
	}

	@Override
	public boolean update(Integer id, Integer planId, PlanForm data) {

		if (!planRepo.existsById(planId)) {
			return false;
		} else {

			Plan pEntity = new Plan();
			BeanUtils.copyProperties(data, pEntity);
			pEntity.setPlanId(planId);
			//pEntity.setPlanActiveStatus(AppConstants.STR_INACTIVE);
			
			Optional<User> optional = userRepo.findById(id);
			
			if (optional.isPresent()) {
				User user = optional.get();
				pEntity.setUseruId(user);
				pEntity.setCreatedBy(user.getCreatedBy());
				pEntity.setUpdatedBy(id);
				System.out.println(user);
			}

			pEntity.setUpdatedBy(id);

			planRepo.save(pEntity); // upsert (insert / update based on id)
			return true;
		}
	}

	@Override
	public List<Plan> getAllPlans() {

		return planRepo.findAll();
	}

	@Override
	public String actionById(Integer id, Integer planId) {

		if (!planRepo.existsById(planId)) {
			return AppConstants.STR_FALSE;
		} else {
			Optional<Plan> optional = planRepo.findById(planId);
			Plan plan = optional.get();

			if (plan.getPlanActiveStatus().equals(AppConstants.STR_INACTIVE)) {
				plan.setPlanActiveStatus(AppConstants.STR_ACTIVE);

				plan.setUpdatedBy(id);

				planRepo.save(plan);
				return AppConstants.CHANGE_TO_ACTIVE_STATUS_MSG;
			} else {
				plan.setPlanActiveStatus(AppConstants.STR_INACTIVE);

				plan.setUpdatedBy(id);

				planRepo.save(plan);
				return AppConstants.CHANGE_TO_INACTIVE_STATUS_MSG;
			}

		}
	}

}
