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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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
	
	@Column
	private int minBudget;
	
	@Column
	private int maxBudget;
	
	@Column(name="min_bedrooms")
	private int minBedrooms;
	
	@Column(name="max_bedrooms")
	private int maxBedrooms;
		
	@Column(name="min_bathrooms")
	private int minBathrooms;
	
	@Column(name="max_bathrooms")
	private int maxBathrooms;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@ColumnDefault("admin")
	@Column(name="created_by")
	private String createdBy;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, name="updated_at")
	private Date updatedAt;
	
	@ColumnDefault("admin")
	@Column(name="updated_by")
	private String updatedBy;

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

	public int getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(int minBudget) {
		this.minBudget = minBudget;
	}

	public int getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(int maxBudget) {
		this.maxBudget = maxBudget;
	}

	public int getMinBedrooms() {
		return minBedrooms;
	}

	public void setMinBedrooms(int minBedrooms) {
		this.minBedrooms = minBedrooms;
	}

	public int getMaxBedrooms() {
		return maxBedrooms;
	}

	public void setMaxBedrooms(int maxBedrooms) {
		this.maxBedrooms = maxBedrooms;
	}

	public int getMinBathrooms() {
		return minBathrooms;
	}

	public void setMinBathrooms(int minBathrooms) {
		this.minBathrooms = minBathrooms;
	}

	public int getMaxBathrooms() {
		return maxBathrooms;
	}

	public void setMaxBathrooms(int maxBathrooms) {
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
