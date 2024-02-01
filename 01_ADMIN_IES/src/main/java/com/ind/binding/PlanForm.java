package com.ind.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanForm {
	
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String planCategory;

}
