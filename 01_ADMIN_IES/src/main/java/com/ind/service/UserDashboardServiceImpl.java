package com.ind.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ind.binding.DashboardResponse;
import com.ind.binding.SigninForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserUpdateForm;
import com.ind.constants.AppConstants;
import com.ind.entity.User;
import com.ind.entity.UserRole;
import com.ind.repo.UserRepo;
import com.ind.repo.UserRoleRepo;
import com.ind.utility.EmailUtils;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo roleRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String userLogin(SigninForm data) {

		String s = null;

		User uEntity = userRepo.findByUserEmailAndUserPszzd(data.getUserEmail(), data.getUserPszzd());

		if (uEntity == null) {
			return AppConstants.STR_INVALID_CREDENTIALS_MSG;
		} else {

			if (uEntity.getAccountStatus().equals(AppConstants.STR_LOCKED)) {

				return s = "Your Account is locked , check your mail to unlocked";

			}
			if (uEntity.getUserActiveStatus().equals("INACTIVE")) {

				return s = "Your Account is inactive , contact Admin";

			}

			// UserRole userRoleId = uEntity.getUserRoleId();

			Optional<UserRole> roleEntity = roleRepo.findById(1);

			if (roleEntity.isPresent()) {
				UserRole userRole = roleEntity.get();

				if (uEntity.getUserRoleId().equals(userRole)) {
					s = AppConstants.STR_ADMIN_LOGIN_SUCCESS_MSG;
				} else {
					s = AppConstants.STR_CASEWORKER_SUCCESS_MSG;

				}
				return s;
			}
		}
		return s;
	}

	@Override
	public DashboardResponse dashboard() {

		DashboardResponse response = new DashboardResponse();

		response.setNoOfPlanAvailable(45);
		response.setCitizensApproved(446);
		response.setCitizensDenied(59);
		response.setBenefitsGiven(752985555);

		return response;
	}

	@Override
	public UserEditForm edit(Integer userId) {

		Optional<User> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			UserEditForm ue = new UserEditForm();
			BeanUtils.copyProperties(user, ue);
			return ue;
		}

		return null;
	}

	@Override
	public boolean update(Integer userId, UserUpdateForm data) {

		if (!userRepo.existsById(userId)) {
			return false;
		} else {

			Optional<User> optional = userRepo.findById(userId);
			User user = optional.get();

			// copy user data to entity
			User uEntity = new User();
			BeanUtils.copyProperties(data, uEntity);

			uEntity.setUserId(userId);
			uEntity.setAccountStatus(user.getAccountStatus());
			uEntity.setUserActiveStatus(user.getUserActiveStatus());
			uEntity.setCreatedBy(user.getCreatedBy());
			uEntity.setUpdatedBy(user.getUpdatedBy());
			uEntity.setUserRoleId(user.getUserRoleId());
			uEntity.setPlans(user.getPlans());

			uEntity.setUserEmail(user.getUserEmail());
			uEntity.setUserPszzd(user.getUserPszzd());
			uEntity.setUserSsn(user.getUserSsn());

			// insert record
			userRepo.save(uEntity);

			return true;
		}
	}

	@Override
	public boolean forgotPszzdUser(String email) {

		User cwEntity = userRepo.findByUserEmail(email);

		if (cwEntity == null) {
			return false;
		} else {

			String subject = AppConstants.RECOVER_PSZZD_SUBJECT_MSG;
			String body = AppConstants.EMAIL_BODY_PSZZD_MSG + cwEntity.getUserPszzd();
			emailUtils.sendEmail(email, body, subject);
			return true;
		}
	}

}
