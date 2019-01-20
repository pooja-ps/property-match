package com.radius.propertymatch.service;

public interface IMatchPercentageService {

	public Integer matchBasedOnDistance(Double distance);
	
	public Integer matchBasedOnBudget(Integer price, Integer minBudget, Integer maxBudget);
	
	public Integer matchBasedOnRoom(Integer noOfRooms, Integer minRooms, Integer maxRooms);
	
}
