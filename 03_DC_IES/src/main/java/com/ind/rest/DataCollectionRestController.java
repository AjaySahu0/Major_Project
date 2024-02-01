package com.ind.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ind.binding.EducationDetailsForm;
import com.ind.binding.IncomeDetailsForm;
import com.ind.binding.KidsDetailsForm;
import com.ind.binding.PlanSelectionForm;
import com.ind.service.DataCollectionService;

@RestController
public class DataCollectionRestController {

	@Autowired
	private DataCollectionService dataService;

	@GetMapping("/display_PlanSelection")
	public ResponseEntity<List<String>> displayPlanSelectionPage() {

		List<String> plan = dataService.getPlan();

		return new ResponseEntity<List<String>>(plan, HttpStatus.OK);
	}

	@PostMapping("/create_planSelection")
	public ResponseEntity<String> createPlan(@RequestBody PlanSelectionForm data) {

		System.out.println(data);

		String status = dataService.createPlanSelection(data);

		if (status.equals("Plan has been created")) {
			return new ResponseEntity<String>(status, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/create_incomeDetails")
	public ResponseEntity<String> createIncome(@RequestBody IncomeDetailsForm data) {

		String status = dataService.createIncomeDetails(data);

		if (status.equals("Income Details has been created")) {
			return new ResponseEntity<String>(status, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/create_education")
	public ResponseEntity<String> createEducation(@RequestBody EducationDetailsForm data) {

		System.out.println(data);

		String status = dataService.createEducationDetails(data);
		if (status.equals("Education Details has been created")) {
			return new ResponseEntity<String>(status, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(status, HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/create_kid")
	public ResponseEntity<String> createKid(@RequestBody KidsDetailsForm data) {

		System.out.println(data);

		boolean status = dataService.createKidsDetails(data);

		if (status) {
			return new ResponseEntity<String>("Kids Details has been created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Failed to create the Kids details", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view_summary")
	public ResponseEntity<List<Object>> getSummary(@RequestHeader("caseId") Integer caseId) {

		List<Object> summary = dataService.summary(caseId);

		return new ResponseEntity<List<Object>>(summary, HttpStatus.OK);

	}

}
