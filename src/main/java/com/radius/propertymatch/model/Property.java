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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="property")
public class Property {
	
	public Property() {
	}	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(columnDefinition="INT(11) COMMENT '0 - Rent, 1 - Sale'")
	private Integer type;
	
	@Column
	private Double latitude;
	
	@Column
	private Double longitude;
	
	@Column
	private Integer price;
	
	@Column(name="no_of_bedrooms")
	private Integer noOfBedrooms;
		
	@Column(name="no_of_bathrooms")
	private Integer noOfBathrooms;
	
	@JsonIgnore
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@JsonIgnore
	@Column(name="created_by")
	private String createdBy = "admin";
	
	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, name="updated_at")
	private Date updatedAt;
		
	@JsonIgnore
	@Column(name="updated_by")
	private String updatedBy = "admin";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNoOfBedrooms() {
		return noOfBedrooms;
	}

	public void setNoOfBedrooms(Integer noOfBedrooms) {
		this.noOfBedrooms = noOfBedrooms;
	}

	public Integer getNoOfBathrooms() {
		return noOfBathrooms;
	}

	public void setNoOfBathrooms(Integer noOfBathrooms) {
		this.noOfBathrooms = noOfBathrooms;
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
	
	@Transient
	private int matchPercentage;

	@Transient
	public int getMatchPercentage() {
		return matchPercentage;
	}

	@Transient
	public void setMatchPercentage(int matchPercentage) {
		this.matchPercentage = matchPercentage;
	}
	
}
