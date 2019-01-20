package com.radius.propertymatch.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.service.ISearchRequirementService;

@Controller
@RequestMapping(path = "/searchrequirement")
public class SearchRequirementController {

	@Autowired
	ISearchRequirementService searchRequirementService;

	@PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
	public @ResponseBody List<Property> addSearchRequirement(@RequestBody SearchRequirement searchRequirement,
			HttpServletRequest request, HttpServletResponse response) {
		if (!validateInput(searchRequirement)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		} else {
			searchRequirementService.add(searchRequirement);
			return searchRequirementService.matchRequirementWithProperties(searchRequirement);
		}
	}

	@GetMapping(path = "/bulkadd")
	public @ResponseBody String bulkAddSearchRequirements() {
		try {
			int minPrice = 0, maxPrice = 10000;
			int minRooms = 0, maxRooms = 8;
			int counter = 0;

			JsonFactory jsonfactory = new JsonFactory();
			File source = ResourceUtils.getFile("classpath:json/cities.json");

			JsonParser parser = jsonfactory.createParser(source);

			ArrayList<SearchRequirement> lstSearchRequirement = new ArrayList<SearchRequirement>();
			Random random = new Random();
			int priceRange = (maxPrice - minPrice) + 1;
			int roomRange = (maxRooms - minRooms) + 1;

			while (parser.nextToken() != JsonToken.END_ARRAY) {
				String token = parser.getCurrentName();

				if ("lat".equals(token)) {
					SearchRequirement requirement = new SearchRequirement();
					requirement.setType(random.nextBoolean() ? 1 : 0);

					parser.nextToken();
					requirement.setLatitude(Double.parseDouble(parser.getText()));
					parser.nextToken();
					parser.nextToken();
					requirement.setLongitude(Double.parseDouble(parser.getText()));

					// To simulate if either min budget or max budget is not provided, setting it to
					// null if random boolean gives true, else getting random integer within range.
					Integer minBudget = random.nextBoolean() ? null : random.nextInt(priceRange) + minPrice;

					// If min budget gets set to null, then max budget cannot be null, hence added
					// check to see min budget if null or not
					Integer maxBudget = (minBudget != null
							? (random.nextBoolean() ? null : (random.nextInt(priceRange) + minPrice))
							: (random.nextInt(priceRange) + minPrice));

					requirement.setMinBudget(minBudget);
					requirement.setMaxBudget(maxBudget);

					Integer minBedrooms = random.nextBoolean() ? null : random.nextInt(roomRange) + minRooms;
					Integer maxBedrooms = (minBedrooms != null
							? (random.nextBoolean() ? null : (random.nextInt(roomRange) + minRooms))
							: (random.nextInt(roomRange) + minRooms));
					requirement.setMinBedrooms(minBedrooms);
					requirement.setMaxBedrooms(maxBedrooms);

					Integer minBathrooms = random.nextBoolean() ? null : random.nextInt(roomRange) + minRooms;
					Integer maxBathrooms = (minBathrooms != null
							? (random.nextBoolean() ? null : (random.nextInt(roomRange) + minRooms))
							: (random.nextInt(roomRange) + minRooms));
					requirement.setMinBathrooms(minBathrooms);
					requirement.setMaxBathrooms(maxBathrooms);

					lstSearchRequirement.add(requirement);
					counter++;

					if (counter % 50 == 0) {
						searchRequirementService.bulkAdd(lstSearchRequirement);
						lstSearchRequirement.clear();
					}
				}
			}

			if (lstSearchRequirement.size() != 0) {
				searchRequirementService.bulkAdd(lstSearchRequirement);
			}

			parser.close();
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		return "Populated search requirements";
	}

	@GetMapping(path = "/bulkAddRequirementsGeoInMemory")
	public @ResponseBody String bulkAddRequirementsGeoInMemory() {
		searchRequirementService.bulkAddRequirementsGeoInMemory();
		return "Populated requirements geo data in memory";
	}

	public boolean validateInput(SearchRequirement requirement) {
		// Either min or max should be present for budget, bedroom and bathroom
		if (requirement.getType() == null || requirement.getLatitude() == null || requirement.getLongitude() == null
				|| (requirement.getMinBudget() == null && requirement.getMaxBudget() == null)
				|| (requirement.getMinBedrooms() == null && requirement.getMaxBedrooms() == null)
				|| (requirement.getMinBathrooms() == null && requirement.getMaxBathrooms() == null)) {
			return false;
		} else {
			return true;
		}
	}

}
