package com.ind.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ind.binding.PlanForm;
import com.ind.constants.AppConstants;
import com.ind.entity.Plan;
import com.ind.service.PlanService;

@RestController
public class AdminPlanRestController {

	@Autowired
	private PlanService planService;

	@PostMapping("/create_plan")
	public ResponseEntity<String> createPlan(@RequestHeader("id") Integer id, @RequestBody PlanForm data) {
		
		System.out.println(id);
		System.out.println(data);

		String status = planService.create(id, data);
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}

	@GetMapping("/edit_plan/{planId}")
	public ResponseEntity<PlanForm> edit(@PathVariable("planId") Integer planId) {

		PlanForm editForm = planService.edit(planId);
		if (editForm != null) {
			return new ResponseEntity<PlanForm>(editForm, HttpStatus.OK);
		}
		return null;
	}

	@PutMapping("/update_plan/{planId}")
	public ResponseEntity<String> updatePlan(@RequestHeader("id") Integer id, @PathVariable("planId") Integer planId,
			@RequestBody PlanForm data) {

		boolean status = planService.update(id, planId, data);
		if (status) {
			return new ResponseEntity<String>(AppConstants.PLAN_EDIT_SUCCESS_MSG, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(AppConstants.PLAN_EDIT_FAILED_MSG, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/view_plan")
	public ResponseEntity<List<Plan>> getAllPlan() {

		List<Plan> allPlans = planService.getAllPlans();
		return new ResponseEntity<List<Plan>>(allPlans, HttpStatus.OK);

	}

	@GetMapping("/action_plan/{planId}")
	public ResponseEntity<String> actionPlan(@RequestHeader("id") Integer id, @PathVariable("planId") Integer planId) {
		String status = planService.actionById(id, planId);

		if (AppConstants.STR_FALSE.equals(status)) {
			return new ResponseEntity<String>(AppConstants.FAILED_ACTION_STATUS_MSG, HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
	}

}
