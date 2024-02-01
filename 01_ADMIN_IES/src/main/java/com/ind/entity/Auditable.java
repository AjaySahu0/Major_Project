/*package com.ind.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)

public abstract class Auditable {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDate createdDate;

	@LastModifiedDate
	private LocalDate updatedDate;

	@Column(updatable = false)
	private String createdBy;
	private String updatedBy;

}
*/