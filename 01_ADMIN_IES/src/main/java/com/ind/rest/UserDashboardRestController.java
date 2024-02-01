package com.ind.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ind.binding.DashboardResponse;
import com.ind.binding.ForgotPasswordForm;
import com.ind.binding.SigninForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserUpdateForm;
import com.ind.constants.AppConstants;
import com.ind.service.UserDashboardService;

@RestController
public class UserDashboardRestController {

	@Autowired
	private UserDashboardService dashService;
	

	@PostMapping("/user_login")
	public ResponseEntity<String> loginUser(@RequestBody SigninForm form) {
		

		String status = dashService.userLogin(form);

		return new ResponseEntity<String>(status, HttpStatus.ACCEPTED);

		/*
		 * if (status) { return new
		 * ResponseEntity<String>(AppConstants.STR_USER_SUCCESS_MSG,
		 * HttpStatus.ACCEPTED); } else { return new
		 * ResponseEntity<String>(AppConstants.STR_INVALID_CREDENTIALS_MSG,
		 * HttpStatus.BAD_REQUEST); }
		 */
	}

	@GetMapping("/user_dashboard")
	public ResponseEntity<DashboardResponse> dashboard() {
		DashboardResponse dashboard = dashService.dashboard();

		return new ResponseEntity<DashboardResponse>(dashboard, HttpStatus.OK);
	}

	@GetMapping("/edit_userinside/{userId}")
	public ResponseEntity<UserEditForm> edit(@PathVariable("userId") Integer userId) {

		UserEditForm editForm = dashService.edit(userId);
		if (editForm != null) {
			return new ResponseEntity<UserEditForm>(editForm, HttpStatus.OK);
		}
		return null;

	}

	@PutMapping("/update_userinside/{userId}")
	public ResponseEntity<String> updateCaseWorker(@PathVariable("userId") Integer userId,
			@RequestBody UserUpdateForm data) {
		boolean status = dashService.update(userId, data);

		if (status) {
			return new ResponseEntity<String>(AppConstants.USER_EDITED_SUCCESS_MSG, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(AppConstants.CASEWORKER_EDIT_FAILED_MSG, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/forgotPwd_user")
	public ResponseEntity<String> forgotPwd(@RequestBody ForgotPasswordForm data) {

		System.out.println(data.getUserEmail());

		boolean status = dashService.forgotPszzdUser(data.getUserEmail());

		if (status) {
			return new ResponseEntity<String>(AppConstants.CHECK_YOUR_EMAIL_MSG, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(AppConstants.STR_INVALID_EMAIL, HttpStatus.OK);

		}

	}

}
