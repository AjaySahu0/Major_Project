package com.ind.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ind.binding.EducationDetailsForm;
import com.ind.binding.IncomeDetailsForm;
import com.ind.binding.KidsDetailsForm;
import com.ind.binding.PlanSelectionForm;
import com.ind.entity.CitizenAllData;
import com.ind.entity.EducationDetails;
import com.ind.entity.IncomeDetails;
import com.ind.entity.KidsDetails;
import com.ind.entity.Plan;
import com.ind.entity.PlanSelection;
import com.ind.repo.CitizenRepo;
import com.ind.repo.EducationDetailsRepo;
import com.ind.repo.IncomeDetailsRepo;
import com.ind.repo.KidsDetailsRepo;
import com.ind.repo.PlanRepo;
import com.ind.repo.PlanSelectionRepo;

@Service
public class DataCollectionServiceImpl implements DataCollectionService {

	@Autowired
	private CitizenRepo citizenRepo;

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private PlanSelectionRepo planSelectionRepo;

	@Autowired
	private IncomeDetailsRepo incomeRepo;

	@Autowired
	private EducationDetailsRepo educationRepo;

	@Autowired
	private KidsDetailsRepo kidsRepo;

	// checking the plan is active and plan end date is after current date
	@Override
	public List<String> getPlan() {

		List<String> listPlan = new ArrayList<>();

		List<Plan> planActive = planRepo.findByPlanActiveStatus("ACTIVE");

		LocalDate currentDate = LocalDate.now();

		for (Plan plan : planActive) {
			if (plan.getStartDate().isBefore(currentDate) && plan.getEndDate().isAfter(currentDate)) {
				listPlan.add(plan.getPlanName());
			}
		}
		return listPlan;
	}

	@Override
	public String createPlanSelection(PlanSelectionForm data) {

		PlanSelection planSelData = planSelectionRepo.findByCaseId(data.getCaseId());

		Optional<CitizenAllData> optional = citizenRepo.findById(data.getCaseId());

		if (planSelData == null) {

			if (optional.isPresent()) {

				PlanSelection pentity = new PlanSelection();
				BeanUtils.copyProperties(data, pentity);

				CitizenAllData allData = optional.get();
				pentity.setCpNum(allData);
				planSelectionRepo.save(pentity);
				return "Plan has been created";
			}
			return "No citizen citizen is there with is caseNum";
		} else {
			return "Already plan selection is done with this caseNum";
		}
	}

	@Override
	public String createIncomeDetails(IncomeDetailsForm data) {

		IncomeDetails incomeData = incomeRepo.findByCaseId(data.getCaseId());

		Optional<CitizenAllData> optional = citizenRepo.findById(data.getCaseId());

		if (incomeData == null) {

			if (optional.isPresent()) {

				IncomeDetails ientity = new IncomeDetails();
				BeanUtils.copyProperties(data, ientity);

				CitizenAllData allData = optional.get();
				ientity.setCiNum(allData);
				incomeRepo.save(ientity);
				return "Income Details has been created";
			}
			return "No citizen citizen is there with is caseNum";
		} else {
			return "Already Income Details is done with this caseNum";
		}
	}

	@Override
	public String createEducationDetails(EducationDetailsForm data) {

		EducationDetails eduData = educationRepo.findByCaseId(data.getCaseId());

		Optional<CitizenAllData> optional = citizenRepo.findById(data.getCaseId());

		if (eduData == null) {

			if (optional.isPresent()) {

				EducationDetails eentity = new EducationDetails();
				BeanUtils.copyProperties(data, eentity);

				CitizenAllData allData = optional.get();
				eentity.setCeNum(allData);
				educationRepo.save(eentity);
				return "Education Details has been created";
			}
			return "No citizen citizen is there with is caseNum";
		} else {
			return "Already Education Details is done with this caseNum";
		}
	}

	@Override
	public boolean createKidsDetails(KidsDetailsForm data) {

		if (data != null) {
			KidsDetails kentity = new KidsDetails();
			BeanUtils.copyProperties(data, kentity);

			Optional<CitizenAllData> optional = citizenRepo.findById(data.getCaseId());

			if (optional.isPresent()) {
				CitizenAllData allData = optional.get();
				kentity.setCkNum(allData);
			}
			kidsRepo.save(kentity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Object> summary(Integer caseId) {

		PlanSelection psList = planSelectionRepo.findByCaseId(caseId);

		EducationDetails edList = educationRepo.findByCaseId(caseId);

		IncomeDetails idList = incomeRepo.findByCaseId(caseId);

		List<KidsDetails> kdList = kidsRepo.findByCaseId(caseId);

		List<Object> l = new ArrayList<>();

		l.add(psList);
		l.add(edList);
		l.add(idList);
		l.add(kdList);
		return l;
	}
}
