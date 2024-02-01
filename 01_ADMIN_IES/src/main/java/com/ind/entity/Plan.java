package com.ind.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Data
@Setter
@Getter
@ToString
public class Plan { // extends Auditable implements Serializable

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String planCategory;
	private String planActiveStatus = "ACTIVE";

	// @CreatedDate
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDate createdDate;

	// @LastModifiedDate
	@UpdateTimestamp
	private LocalDate updatedDate;

	@Column(updatable = false)
	private Integer createdBy;
	private Integer updatedBy;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	// @JsonIgnoreProperties("User")
	private User useruId;

}
