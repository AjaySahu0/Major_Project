package com.ind.service;

import java.util.List;

import com.ind.binding.EducationDetailsForm;
import com.ind.binding.IncomeDetailsForm;
import com.ind.binding.KidsDetailsForm;
import com.ind.binding.PlanSelectionForm;

public interface DataCollectionService {

	public List<String> getPlan();

	public String createPlanSelection(PlanSelectionForm data);

	public String createIncomeDetails(IncomeDetailsForm data);

	public String createEducationDetails(EducationDetailsForm data);

	public boolean createKidsDetails(KidsDetailsForm data);

	public List<Object> summary(Integer caseId);

}
