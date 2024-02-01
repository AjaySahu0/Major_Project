package com.ind.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class KidsDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer kId;

	private Integer caseId;

	private String kidName;

	private Integer kidAge;

	private String kidSsn;
	
	@ManyToOne
	@JoinColumn(name = "case_Num")
	@JsonBackReference
	private CitizenAllData ckNum;

}
