package com.ind.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ind.binding.EligDeterForm;
import com.ind.entity.EligibilityDeterminationTable;
import com.ind.service.EligibilityDeterminationService;

@RestController
public class EligibilityRestController {

	@Autowired
	private EligibilityDeterminationService eligiService;

	@GetMapping("/eligi_Status")
	public ResponseEntity<EligibilityDeterminationTable> EligibilityStatus(@RequestBody EligDeterForm data) {

		EligibilityDeterminationTable checkEligibility = eligiService.checkEligibility(data);
		return new ResponseEntity<EligibilityDeterminationTable>(checkEligibility, HttpStatus.OK);

	}

}
