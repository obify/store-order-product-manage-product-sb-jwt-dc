package com.assignment.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<U> {
	
	@JsonIgnore
	@CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_AT")
    private Date createdAt;

	@JsonIgnore
    @CreatedBy
    @Column(name = "CREATED_BY")
    private U createdBy;

	@JsonIgnore
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

	@JsonIgnore
    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private U updatedBy;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public U getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public U getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(U updatedBy) {
		this.updatedBy = updatedBy;
	}

}
