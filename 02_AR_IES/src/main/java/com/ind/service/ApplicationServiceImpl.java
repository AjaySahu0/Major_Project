package com.ind.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ind.binding.CitizenBasic;
import com.ind.constants.AppConstants;
import com.ind.entity.CitizenAllData;
import com.ind.entity.User;
import com.ind.repo.CitizenRepo;
import com.ind.repo.UserRepo;
import com.ind.request.CitizenRequest;
import com.ind.response.CitizenResponse;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private CitizenRepo crepo;

	@Autowired
	private UserRepo userRepo;

	private String CHECK_SSN_URL = "http://65.2.166.5:8080/ssa/citizen";

	@Override
	public String createApplication(Integer id, CitizenBasic data) {

		CitizenRequest cr = new CitizenRequest();
		cr.setFullname(data.getFullname());
		cr.setDob(data.getDob());
		cr.setGender(data.getGender());
		cr.setSsn(data.getSsn());

		System.out.println(data);

		Optional<CitizenAllData> findBySsn = crepo.findBySsn(data.getSsn());

		if (findBySsn.isPresent()) {

			CitizenAllData allData = findBySsn.get();

			if (allData.getSsn().equals(data.getSsn())) {
				return "citizen is already applied";
			}
		}

		else {

			RestTemplate rt = new RestTemplate();
			ResponseEntity<CitizenResponse> respEntity = rt.postForEntity(CHECK_SSN_URL, cr, CitizenResponse.class);
			CitizenResponse body = respEntity.getBody();

			System.out.println(body);

			if (AppConstants.STR_RHODE_ISLAND.equals(body.getState())) {

				// copy the citizen data to the table
				CitizenAllData cd = new CitizenAllData();
				BeanUtils.copyProperties(body, cd);
				cd.setEmail(data.getEmail());
				cd.setPhno(data.getPhno());
				cd.setCreatedBy(id);

				Optional<User> optional = userRepo.findById(id);
				if (optional.isPresent()) {
					User user = optional.get();

					cd.setUsercId(user);
				}
				crepo.save(cd);

				return AppConstants.APPLICATION_CREATED_MSG;
			} else {
				return AppConstants.APPLICATION_FAILED_MSG;
			}
		}
		return null;
	}

	@Override
	public List<CitizenAllData> getApplication(Integer id) {

		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();

			return crepo.findByUsercId(user);
		}

		return null;

	}

}
