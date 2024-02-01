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

import com.ind.binding.CitizenBasic;
import com.ind.constants.AppConstants;
import com.ind.entity.CitizenAllData;
import com.ind.service.ApplicationService;

@RestController
public class ApplicationRestController {

	@Autowired
	private ApplicationService appService;

	@PostMapping("/create_app")
	public ResponseEntity<String> createApplication(@RequestHeader("id") Integer id, @RequestBody CitizenBasic data) {

		String status = appService.createApplication(id, data);

		if (status.equals(AppConstants.APPLICATION_CREATED_MSG)) {
			return new ResponseEntity<String>(AppConstants.APPLICATION_CREATED_MSG, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view_app")
	public ResponseEntity<List<CitizenAllData>> getAllApplication(@RequestHeader("id") Integer id) {

		List<CitizenAllData> allApplication = appService.getApplication(id);
		return new ResponseEntity<List<CitizenAllData>>(allApplication, HttpStatus.OK);
	}
}
