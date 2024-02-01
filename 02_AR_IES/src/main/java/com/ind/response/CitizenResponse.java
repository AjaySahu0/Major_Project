package com.ind.response;

import lombok.Data;

@Data
public class CitizenResponse {
	
	private String fullname;
	private String gender;
	private String dob;
	private String ssn;

	private String city;
	private String houseNum;
	private String state;

}
