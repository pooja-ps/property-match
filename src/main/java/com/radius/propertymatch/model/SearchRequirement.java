package com.radius.propertymatch.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="search_requirement")
public class SearchRequirement {

	public SearchRequirement() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private int type;
	
	@Column
	private double latitude;
	
	@Column
	private double longitude;
	
	@Column(name="min_budget")
	private Integer minBudget;
	
	@Column(name="max_budget")
	private Integer maxBudget;
	
	@Column(name="min_bedrooms")
	private Integer minBedrooms;
	
	@Column(name="max_bedrooms")
	private Integer maxBedrooms;
		
	@Column(name="min_bathrooms")
	private Integer minBathrooms;
	
	@Column(name="max_bathrooms")
	private Integer maxBathrooms;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="created_by")
	private String createdBy = "admin";
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, name="updated_at")
	private Date updatedAt;
		
	@Column(name="updated_by")
	private String updatedBy = "admin";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Integer getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(Integer minBudget) {
		this.minBudget = minBudget;
	}

	public Integer getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(Integer maxBudget) {
		this.maxBudget = maxBudget;
	}

	public Integer getMinBedrooms() {
		return minBedrooms;
	}

	public void setMinBedrooms(Integer minBedrooms) {
		this.minBedrooms = minBedrooms;
	}

	public Integer getMaxBedrooms() {
		return maxBedrooms;
	}

	public void setMaxBedrooms(Integer maxBedrooms) {
		this.maxBedrooms = maxBedrooms;
	}

	public Integer getMinBathrooms() {
		return minBathrooms;
	}

	public void setMinBathrooms(Integer minBathrooms) {
		this.minBathrooms = minBathrooms;
	}

	public Integer getMaxBathrooms() {
		return maxBathrooms;
	}

	public void setMaxBathrooms(Integer maxBathrooms) {
		this.maxBathrooms = maxBathrooms;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
