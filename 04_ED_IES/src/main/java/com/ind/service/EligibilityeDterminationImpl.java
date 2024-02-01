package com.ind.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ind.binding.EligDeterForm;
import com.ind.entity.CitizenAllData;
import com.ind.entity.EducationDetails;
import com.ind.entity.EligibilityDeterminationTable;
import com.ind.entity.IncomeDetails;
import com.ind.entity.KidsDetails;
import com.ind.entity.PlanSelection;
import com.ind.repo.CitizenRepo;
import com.ind.repo.EducationDetailsRepo;
import com.ind.repo.EligibilityDeterminationRepo;
import com.ind.repo.IncomeDetailsRepo;
import com.ind.repo.KidsDetailsRepo;
import com.ind.repo.PlanSelectionRepo;

@Service
public class EligibilityeDterminationImpl implements EligibilityDeterminationService {

	@Autowired
	private CitizenRepo citizenRepo;

	@Autowired
	private PlanSelectionRepo planSelectionRepo;

	@Autowired
	private IncomeDetailsRepo incomeRepo;

	@Autowired
	private EducationDetailsRepo educationRepo;

	@Autowired
	private KidsDetailsRepo kidsRepo;

	@Autowired
	private EligibilityDeterminationRepo eligiDeterRepo;

	@Override
	public EligibilityDeterminationTable checkEligibility(EligDeterForm data) {

		PlanSelection planSelectionEntity = planSelectionRepo.findByCaseId(data.getCaseNum());
		EligibilityDeterminationTable edEntity = new EligibilityDeterminationTable();
		edEntity.setPlanName(planSelectionEntity.getPlanName());
		edEntity.setCaseId(data.getCaseNum());

		// getting the total income
		IncomeDetails inEntity = incomeRepo.findByCaseId(data.getCaseNum());
		Integer totalIncome = inEntity.getMonthlySalaryIncome() + inEntity.getPropertyIncome()
				+ inEntity.getRentIncome();

		// getting salary income
		Integer monthlySalaryIncome = inEntity.getMonthlySalaryIncome();

		// getting the age of senior citizen

		String dob = null;
		Optional<CitizenAllData> optional = citizenRepo.findById(data.getCaseNum());
		if (optional.isPresent()) {
			CitizenAllData citizenAllData = optional.get();
			edEntity.setCedNum(citizenAllData);
			dob = citizenAllData.getDob();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(dateOfBirth, currentDate);
		Integer CitizenAge = period.getYears();

		// checking the conditions

		if (planSelectionEntity.getPlanName().equalsIgnoreCase("snap")) {
			if (totalIncome < (300 * 80)) {

				edEntity.setBenefitAmt(350 * 80);
				//edEntity.setDenialReason("NA");
				edEntity.setEligStateDate(currentDate.plusDays(6));
				edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
				edEntity.setPlanStatus("Approved");

				return eligiDeterRepo.save(edEntity);
			} else {

				// edEntity.setBenefitAmt(Integer.parseInt("NA"));
				edEntity.setDenialReason("Income condition failed");
				// edEntity.setEligStateDate();
				// edEntity.setEligEndDate();
				edEntity.setPlanStatus("Denied");
				return eligiDeterRepo.save(edEntity);

			}
		} else if (planSelectionEntity.getPlanName().equalsIgnoreCase("ccap")) {

			List<KidsDetails> list = kidsRepo.findByCaseId(data.getCaseNum());
			Integer ag = 0;
			for (KidsDetails kidsDetails : list) {
				if (kidsDetails.getKidAge() < 16) {
					ag++;
				}
			}
			System.out.println(list.size());
			System.out.println(ag);

			if (totalIncome < (300 * 80) && list.size() == ag) {

				edEntity.setBenefitAmt(250 * 80);
				//edEntity.setDenialReason("NA");
				edEntity.setEligStateDate(currentDate.plusDays(6));
				edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
				edEntity.setPlanStatus("Approved");

				return eligiDeterRepo.save(edEntity);

			} else {
				if (totalIncome > (300 * 80)) {

					// edEntity.setBenefitAmt(Integer.parseInt("NA"));
					edEntity.setDenialReason("Income condition failed");
					// edEntity.setEligStateDate();
					// edEntity.setEligEndDate();
					edEntity.setPlanStatus("Denied");

					return eligiDeterRepo.save(edEntity);

				} else {

					// edEntity.setBenefitAmt(Integer.parseInt("NA"));
					edEntity.setDenialReason("kid age condition failed");
					// edEntity.setEligStateDate();
					// edEntity.setEligEndDate();
					edEntity.setPlanStatus("Denied");

					return eligiDeterRepo.save(edEntity);

				}

			}
		} else if (planSelectionEntity.getPlanName().equalsIgnoreCase("medicaid")) {

			if (totalIncome < (320 * 80)) {

				edEntity.setBenefitAmt(450 * 80);
				//edEntity.setDenialReason("NA");
				edEntity.setEligStateDate(currentDate.plusDays(6));
				edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
				edEntity.setPlanStatus("Approved");

				return eligiDeterRepo.save(edEntity);
			} else {

				// edEntity.setBenefitAmt(Integer.parseInt("NA"));
				edEntity.setDenialReason("Income condition failed");
				// edEntity.setEligStateDate();
				// edEntity.setEligEndDate();
				edEntity.setPlanStatus("Denied");
				return eligiDeterRepo.save(edEntity);

			}
		} else if (planSelectionEntity.getPlanName().equalsIgnoreCase("medicare")) {

			if (CitizenAge > 65) {

				edEntity.setBenefitAmt(550 * 80);
				//edEntity.setDenialReason("NA");
				edEntity.setEligStateDate(currentDate.plusDays(6));
				edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
				edEntity.setPlanStatus("Approved");

				return eligiDeterRepo.save(edEntity);
			} else {

				// edEntity.setBenefitAmt(Integer.parseInt("NA"));
				edEntity.setDenialReason("Citizen age condition failed");
				// edEntity.setEligStateDate();
				// edEntity.setEligEndDate();
				edEntity.setPlanStatus("Denied");
				return eligiDeterRepo.save(edEntity);

			}
		} else if (planSelectionEntity.getPlanName().equalsIgnoreCase("RIW")) {

			EducationDetails educationEntity = educationRepo.findByCaseId(data.getCaseNum());
			if (monthlySalaryIncome == 0 && educationEntity.getGraduationYear() == null) {

				edEntity.setBenefitAmt(200 * 80);
				//edEntity.setDenialReason("NA");
				edEntity.setEligStateDate(currentDate.plusDays(6));
				edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
				edEntity.setPlanStatus("Approved");
				return eligiDeterRepo.save(edEntity);

			} else {
				if (educationEntity.getGraduationYear() != null) {

					// edEntity.setBenefitAmt(Integer.parseInt("NA"));
					edEntity.setDenialReason("Graduation condition failed");
					// edEntity.setEligStateDate();
					// edEntity.setEligEndDate();
					edEntity.setPlanStatus("Denied");

					return eligiDeterRepo.save(edEntity);

				} else {

					// edEntity.setBenefitAmt(Integer.parseInt("NA"));
					edEntity.setDenialReason("Income condition failed");
					// edEntity.setEligStateDate();
					// edEntity.setEligEndDate();
					edEntity.setPlanStatus("Denied");

					return eligiDeterRepo.save(edEntity);

				}
			}
		}else if(planSelectionEntity.getPlanName().equalsIgnoreCase("QHP")) {
			
			edEntity.setBenefitAmt(90 * 80);
			//edEntity.setDenialReason("NA");
			edEntity.setEligStateDate(currentDate.plusDays(6));
			edEntity.setEligEndDate((currentDate.plusDays(6)).plusMonths(6));
			edEntity.setPlanStatus("Approved");
			return eligiDeterRepo.save(edEntity);
			
		}
		return null;
	}

}
