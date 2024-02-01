package com.ind.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

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
public class EligibilityDeterminationTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer edId;
	private Integer caseId;
	private String planName;
	private String planStatus;
	private LocalDate eligStateDate;
	private LocalDate eligEndDate;
	private Integer benefitAmt;
	private String DenialReason;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@OneToOne
	@JoinColumn(name = "case_Num")
	@JsonBackReference
	private CitizenAllData cedNum;
}
