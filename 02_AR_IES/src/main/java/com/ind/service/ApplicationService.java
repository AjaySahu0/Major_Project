package com.ind.service;

import java.util.List;

import com.ind.binding.CitizenBasic;
import com.ind.entity.CitizenAllData;

public interface ApplicationService {

	public String createApplication(Integer id, CitizenBasic data);

	public List<CitizenAllData> getApplication(Integer id);

}
