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

import com.ind.binding.UnlockUserForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserForm;
import com.ind.binding.UserUpdateForm;
import com.ind.constants.AppConstants;
import com.ind.entity.User;
import com.ind.service.UserService;

@RestController
public class AdminUserRestContoller {

	@Autowired
	private UserService userService;

	@PostMapping("/create_user")
	public ResponseEntity<String> createUser(@RequestHeader("id") Integer id, @RequestBody UserForm data) {
		String status = userService.create(id, data);
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}

	@GetMapping("/edit_user/{userId}")
	public ResponseEntity<UserEditForm> edit(@PathVariable("userId") Integer userId) {

		UserEditForm editForm = userService.edit(userId);
		if (editForm != null) {
			return new ResponseEntity<UserEditForm>(editForm, HttpStatus.OK);
		}
		return null;

	}

	@PutMapping("/update_user/{userId}")
	public ResponseEntity<String> updateCaseWorker(@RequestHeader("id") Integer id,
			@PathVariable("userId") Integer userId, @RequestBody UserUpdateForm data) {
		
		boolean status = userService.update(id, userId, data);

		if (status) {
			return new ResponseEntity<String>(AppConstants.USER_EDITED_SUCCESS_MSG, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(AppConstants.CASEWORKER_EDIT_FAILED_MSG, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view_user")
	public ResponseEntity<List<User>> viewAllUser(@RequestHeader("id") Integer id) {
		List<User> allData = userService.getAllData(id);

		return new ResponseEntity<List<User>>(allData, HttpStatus.OK);

	}

	@GetMapping("/action_user/{userId}")
	public ResponseEntity<String> actionUser(@RequestHeader("id") Integer id, @PathVariable("userId") Integer userId) {
		
		String status = userService.actionByIdActiveStatus(id, userId);

		if (status.equals(AppConstants.STR_FALSE)) {
			return new ResponseEntity<String>(AppConstants.FAILED_ACTION_STATUS_MSG, HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
	}

	@GetMapping("/action_userRole/{userId}")
	public ResponseEntity<String> actionUserRole(@RequestHeader("id") Integer id,
			@PathVariable("userId") Integer userId) {
		String status = userService.actionByIdRoleStatus(id, userId);

		if (status.equals(AppConstants.STR_FALSE)) {
			return new ResponseEntity<String>("User doesn't exists", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
	}

	@PostMapping("/unlock_user/{email}")
	public ResponseEntity<String> unlockUser(@PathVariable("email") String email, @RequestBody UnlockUserForm form) {

		System.out.println(email);
		System.out.println(form);

		if (form.getNewPszzd().equals(form.getConformPszzd())) {
			boolean status = userService.unlockUserAccount(email, form);
			if (status) {
				return new ResponseEntity<String>(AppConstants.UNLOCKED_SUCCESS_MSG, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(AppConstants.TEMPORARY_PSZZD_WRONG_MSG, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>(AppConstants.NEW_AND_CONFORM_PSZZD_NOT_MATCHING_MSG,
					HttpStatus.BAD_REQUEST);
		}

	}

}
