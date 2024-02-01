package com.ind.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CitizenAllData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer caseNum;
	private String fullname;
	private String email;
	private String phno;
	private String gender;
	private String dob;
	private String ssn;

	private String city;
	private String houseNum;
	private String state;

	// private String status;
	// private String planChoosen;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDate createdDate;

	private Integer createdBy;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	// @JsonIgnoreProperties("User")
	private User usercId;

	// private createdBy;

	@OneToOne(mappedBy = "cpNum", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private PlanSelection pSelects;

	@OneToOne(mappedBy = "ceNum", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private EducationDetails eDetails;

	@OneToOne(mappedBy = "ciNum", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private IncomeDetails iDetails;

	@OneToMany // (mappedBy = "ckNum")
	@JsonBackReference
	private List<KidsDetails> kDetails;

	// mapping with ED module data
	@OneToOne(mappedBy = "cedNum", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private EligibilityDeterminationTable eliDetermination;

}
