package com.radius.propertymatch.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.service.IMatchPercentageService;

@Service
@PropertySource("classpath:configurations.properties")
@ConfigurationProperties
public class MatchPercentageServiceImpl implements IMatchPercentageService {

	private Integer minDistance;
	private Integer maxDistancePercentage;
	private Integer minBudgetDiff;
	private Integer maxBudgetDiff;
	private Integer maxBudgetPercentage;
	private Integer maxRoomDiff;
	private Integer maxRoomPercentage;

	@Override
	public Integer matchBasedOnDistance(Double distance) {
		// If the distance is within 2 miles (minDistance) , distance contribution for the match percentage is fully 30% (maxDistancePercentage)
		if (distance <= minDistance) {
			return maxDistancePercentage;
		} else {
			//Match percentage is more if distance is less and vice-versa
			return (int) ((minDistance * maxDistancePercentage) / distance);
		}
	}

	@Override
	public Integer matchBasedOnBudget(Integer price, Integer minBudget, Integer maxBudget) {
		if (minBudget!=null && maxBudget!=null) {
			//If the budget is within min and max budget, budget contribution for the match percentage is full 30% (maxBudgetPercentage). 
			//Here we have assumed that even if min and max are given but if the price is not within it but its with within +/- 10% then its a full match
			//If difference is greater than maxBudgetDiff (25%) then match percentage is 0
			if (price >= minBudget && price <= maxBudget) {
				return maxBudgetPercentage; 
			} else if (price < minBudget) {
				Integer diff = ((minBudget - price) / minBudget) * minBudgetDiff;
				if (diff <= maxBudgetDiff) {
					return matchPercentageByBudget(diff);
				}
				return 0;
			} else if (price > maxBudget) {
				Integer diff = ((maxBudget - price) / maxBudget) * minBudgetDiff;
				if (diff <= maxBudgetDiff) {
					return matchPercentageByBudget(diff);
				}
				return 0;
			}
		} else if (minBudget!=null) { //If min or max is not given, +/- 10% budget is a full 30% match.  
			Integer diff = ((minBudget - price) / minBudget) * minBudgetDiff;
			if (diff <= maxBudgetDiff) {
				return matchPercentageByBudget(diff);
			}
			return 0;
		} else {
			Integer diff = ((maxBudget - price) / maxBudget) * minBudgetDiff;
			if (diff <= maxBudgetDiff) {
				return matchPercentageByBudget(diff);
			}
			return 0;
		}
		return 0;
	}

	@Override
	public Integer matchBasedOnRoom(Integer noOfRooms, Integer minRooms, Integer maxRooms) {
		if (minRooms!=null && maxRooms!=null) {
			//If bedroom and bathroom fall between min and max, each will contribute full 20% (maxRoomPercentage)
			//else we have assumed that if the difference is 1, match percentage of room is maxRoomPercentage/2 i.e., 10% 
			//and if difference is 2, match percentage of room is maxRoomPercentage/4 i.e., 5%
			//if difference is > 2 then its 0
			if (noOfRooms >= minRooms && noOfRooms <= maxRooms) {
				return maxRoomPercentage;
			}else if (noOfRooms < minRooms) {
				Integer diff = (minRooms - noOfRooms);
				if (diff <= maxRoomDiff) {
					return matchPercentageOfRoom(diff);
				}
				return 0;
			} else if (noOfRooms > maxRooms) {
				Integer diff = (maxRooms - noOfRooms);
				if (diff <= maxRoomDiff) {
					return matchPercentageOfRoom(diff);
				}
				return 0;
			}
		} else if (minRooms!=null) {
			Integer diff = (minRooms - noOfRooms);
			if (diff <= maxRoomDiff) {
				return matchPercentageOfRoom(diff);
			}
			return 0;
		} else {
			Integer diff = (maxRooms - noOfRooms);
			if (diff <= maxRoomDiff) {
				return matchPercentageOfRoom(diff);
			}
			return 0;
		}
		return 0;
	}

	public Integer matchPercentageByBudget(Integer diff) {
		if(diff<=minBudgetDiff) {
			return maxBudgetPercentage;
		}
		return (minBudgetDiff * maxBudgetPercentage)/diff;
	}
	
	public Integer matchPercentageOfRoom(Integer diff) {
		if(diff==0) {
			return maxRoomPercentage;
		}
		if(diff==1) {
			return maxRoomPercentage/2;
		}
		if(diff==2) {
			return maxRoomPercentage/4;
		}
		return 0;
	}

	public Integer getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(Integer minDistance) {
		this.minDistance = minDistance;
	}

	public Integer getMaxDistancePercentage() {
		return maxDistancePercentage;
	}

	public void setMaxDistancePercentage(Integer maxDistancePercentage) {
		this.maxDistancePercentage = maxDistancePercentage;
	}

	public Integer getMinBudgetDiff() {
		return minBudgetDiff;
	}

	public void setMinBudgetDiff(Integer minBudgetDiff) {
		this.minBudgetDiff = minBudgetDiff;
	}

	public Integer getMaxBudgetDiff() {
		return maxBudgetDiff;
	}

	public void setMaxBudgetDiff(Integer maxBudgetDiff) {
		this.maxBudgetDiff = maxBudgetDiff;
	}

	public Integer getMaxBudgetPercentage() {
		return maxBudgetPercentage;
	}

	public void setMaxBudgetPercentage(Integer maxBudgetPercentage) {
		this.maxBudgetPercentage = maxBudgetPercentage;
	}

	public Integer getMaxRoomDiff() {
		return maxRoomDiff;
	}

	public void setMaxRoomDiff(Integer maxRoomDiff) {
		this.maxRoomDiff = maxRoomDiff;
	}

	public Integer getMaxRoomPercentage() {
		return maxRoomPercentage;
	}

	public void setMaxRoomPercentage(Integer maxRoomPercentage) {
		this.maxRoomPercentage = maxRoomPercentage;
	}
	
	

}
