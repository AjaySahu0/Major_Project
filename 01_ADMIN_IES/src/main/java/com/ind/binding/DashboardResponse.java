package com.ind.binding;

import lombok.Data;

@Data
public class DashboardResponse {
	
	private Integer noOfPlanAvailable;
	private Integer citizensApproved;
	private Integer citizensDenied;
	private Integer benefitsGiven;

}
