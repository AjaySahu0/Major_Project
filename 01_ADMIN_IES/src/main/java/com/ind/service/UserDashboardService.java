package com.ind.service;

import com.ind.binding.DashboardResponse;
import com.ind.binding.SigninForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserUpdateForm;

public interface UserDashboardService {

	public String userLogin(SigninForm data);
	
	//public boolean checkAdmin();

	public DashboardResponse dashboard();
	
	public UserEditForm edit(Integer userId); 

	public boolean update(Integer userId, UserUpdateForm data);

	public boolean forgotPszzdUser(String email);

}
