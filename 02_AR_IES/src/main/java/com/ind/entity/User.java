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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User { // extends Auditable

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fullName;
	private String userEmail;
	private String userPszzd;
	private Integer userPhno;
	private String userGender;
	private String userDob;
	private Integer userSsn;

	private String accountStatus = "LOCKED";

	private String userActiveStatus = "INACTIVE";

	// @CreatedDate
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdDate;

	// @LastModifiedDate
	@UpdateTimestamp
	private LocalDate updatedDate;

	@Column(updatable = false)
	private Integer createdBy;
	private Integer updatedBy;

	@ManyToOne
	@JoinColumn(name = "role_id")
	@JsonBackReference
	// @JsonIgnoreProperties("userRole")
	private UserRole userRoleId;

	@OneToMany(mappedBy = "useruId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Plan> plans;

	@OneToMany(mappedBy = "usercId") // , cascade = CascadeType.ALL, fetch = FetchType.EAGER
	@JsonBackReference
	private List<CitizenAllData> citizens;

}
