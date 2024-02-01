package com.ind.runner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ind.entity.User;
import com.ind.entity.UserRole;
import com.ind.repo.UserRepo;
import com.ind.repo.UserRoleRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo roleRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// roleRepo.deleteAll();

		Optional<UserRole> roles = roleRepo.findById(1);
		if (roles.isEmpty()) {
			UserRole ur1 = new UserRole();
			ur1.setRoleName("ADMIN");

			UserRole ur2 = new UserRole();
			ur2.setRoleName("USER");

			List<UserRole> list = Arrays.asList(ur1, ur2);

			roleRepo.saveAll(list);
		}

		// userRepo.deleteAll();

		Optional<User> users = userRepo.findById(1);

		if (users.isEmpty()) {

			User u = new User();
			u.setFullName("Main Admin");
			u.setUserEmail("admin@gmail.com");
			u.setUserPszzd("abc@123");
			u.setUserPhno(987456321);
			u.setUserGender("male");
			u.setUserDob("2000-01-01");
			u.setUserSsn(12345);
			u.setAccountStatus("UNLOCKED");
			u.setUserActiveStatus("ACTIVE");
			u.setCreatedBy(1);
			u.setUpdatedBy(1);

			Optional<UserRole> optional = roleRepo.findById(1);
			if (optional.isPresent()) {
				UserRole userRole = optional.get();
				u.setUserRoleId(userRole);

			}
			userRepo.save(u);
		}

	}

}
