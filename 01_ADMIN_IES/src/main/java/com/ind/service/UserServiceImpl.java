package com.ind.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ind.binding.UnlockUserForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserForm;
import com.ind.binding.UserUpdateForm;
import com.ind.constants.AppConstants;
import com.ind.entity.User;
import com.ind.entity.UserRole;
import com.ind.repo.UserRepo;
import com.ind.repo.UserRoleRepo;
import com.ind.utility.EmailUtils;
import com.ind.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Override
	public String create(Integer id, UserForm data) {

		User findByUserEmail = userRepo.findByUserEmail(data.getUserEmail());
		if (findByUserEmail != null) {
			return "Already Account is there with this email";
		}

		// copy user data to entity
		User uEntity = new User();
		BeanUtils.copyProperties(data, uEntity);

		// generate pwd
		String randomPwzzd = PwdUtils.generateRandomPwd();
		uEntity.setUserPszzd(randomPwzzd);

		// set createdby
		uEntity.setCreatedBy(id);
		uEntity.setUpdatedBy(id);

		// set role in the user to user
		Optional<UserRole> optional = userRoleRepo.findById(2);
		if (optional.isPresent()) {

			UserRole userRole = optional.get();
			uEntity.setUserRoleId(userRole);
		}

		// insert record
		userRepo.save(uEntity);

		// send email to unlock the account

		String to = data.getUserEmail();
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;

		StringBuffer body = new StringBuffer();
		body.append("<h1> use below temporary password to unlock the Account</h1>");
		body.append("Temporary Password =" + randomPwzzd);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8081/unlock/" + to + "\">click here to unlock your account</a>");

		emailUtils.sendEmail(to, body.toString(), subject);
		return AppConstants.ACCOUNT_CREATED_AND_EMAIL_SENT_MSG;
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
	public boolean update(Integer id, Integer userId, UserUpdateForm data) {

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
			uEntity.setUpdatedBy(id);
			uEntity.setUserRoleId(user.getUserRoleId());
			uEntity.setPlans(user.getPlans());

			uEntity.setUserEmail(user.getUserEmail());
			uEntity.setUserPszzd(user.getUserPszzd());
			uEntity.setUserSsn(user.getUserSsn());

			// insert record
			userRepo.save(uEntity);

			// insert record
			userRepo.save(uEntity);

			return true;
		}
	}

	@Override
	public List<User> getAllData(Integer id) {

		if (id == 1) {
			List<User> users = userRepo.findAll();
			users.remove(0);
			return users;
		}

		Optional<UserRole> optional = userRoleRepo.findById(2);
		if (optional.isPresent()) {
			UserRole userRole = optional.get();

			return userRepo.findByUserRoleId(userRole);
		}
		return null;

	}

	@Override
	public String actionByIdActiveStatus(Integer id, Integer userId) {

		if (!userRepo.existsById(userId)) {
			return AppConstants.STR_FALSE;
		} else {
			Optional<User> optional = userRepo.findById(userId);
			User u = optional.get();

			if (u.getUserActiveStatus().equals(AppConstants.STR_INACTIVE)) {
				u.setUserActiveStatus(AppConstants.STR_ACTIVE);
				u.setUpdatedBy(id);
				userRepo.save(u);
				return AppConstants.CHANGE_TO_ACTIVE_STATUS_MSG;
			} else {
				u.setUserActiveStatus(AppConstants.STR_INACTIVE);
				u.setUpdatedBy(id);

				userRepo.save(u);
				return AppConstants.CHANGE_TO_INACTIVE_STATUS_MSG;
			}
		}
	}

	@Override
	public String actionByIdRoleStatus(Integer id, Integer userId) {

		if (!userRepo.existsById(userId)) {
			return AppConstants.STR_FALSE;
		} else {
			Optional<User> optional = userRepo.findById(userId);
			User u = optional.get();

			Optional<UserRole> roleUser = userRoleRepo.findById(2);
			if (roleUser.isPresent()) {

				UserRole userRole = roleUser.get();

				if (u.getUserRoleId().equals(userRole)) {

					Optional<UserRole> roleAminOptional = userRoleRepo.findById(1);
					if (roleAminOptional.isPresent()) {

						UserRole roleAmin = roleAminOptional.get();

						u.setUserRoleId(roleAmin);
						u.setUpdatedBy(id);
						userRepo.save(u);
						return "User changed to Admin";
					}
				} else {
					Optional<UserRole> roleUserOptional = userRoleRepo.findById(2);
					if (roleUserOptional.isPresent()) {

						UserRole roleUserr = roleUserOptional.get();

						u.setUserRoleId(roleUserr);

						u.setUpdatedBy(id);

						userRepo.save(u);
						return "Admin changed to User";
					}
				}
			}

		}
		return null;
	}

	@Override
	public boolean unlockUserAccount(String email, UnlockUserForm form) {

		User cwEntity = userRepo.findByUserEmail(email);
		if (cwEntity.getUserPszzd().equals(form.getTmpPszzd())) {
			cwEntity.setUserPszzd(form.getNewPszzd());
			cwEntity.setAccountStatus(AppConstants.STR_UNLOCKED);
			userRepo.save(cwEntity);
			return true;
		}

		return false;
	}

}
