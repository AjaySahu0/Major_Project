package com.ind.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class IncomeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iId;

	private Integer caseId;

	private Integer monthlySalaryIncome;

	private Integer rentIncome;

	private Integer propertyIncome;
	
	@OneToOne
	@JoinColumn(name = "case_Num")
	@JsonBackReference
	private CitizenAllData ciNum;

}
