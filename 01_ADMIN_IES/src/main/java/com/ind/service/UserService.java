package com.ind.service;

import java.util.List;

import com.ind.binding.UnlockUserForm;
import com.ind.binding.UserEditForm;
import com.ind.binding.UserForm;
import com.ind.binding.UserUpdateForm;
import com.ind.entity.User;

public interface UserService {

	public String create(Integer userId, UserForm data);
	
	public UserEditForm edit (Integer userId);

	public boolean update(Integer id ,Integer userId, UserUpdateForm data);

	public List<User> getAllData(Integer id);

	public String actionByIdActiveStatus(Integer id ,Integer userId);
	
	public String actionByIdRoleStatus(Integer id ,Integer userId);

	public boolean unlockUserAccount(String email, UnlockUserForm form);

}
