package com.radius.propertymatch.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
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
import com.radius.propertymatch.service.IPropertyService;

@Controller
@RequestMapping(path = "/property")
public class PropertyController {

	@Autowired
	IPropertyService propertyService;

	@PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
	public @ResponseBody List<SearchRequirement> addProperty(@RequestBody Property property, HttpServletRequest request,
			HttpServletResponse response) {
		if (!validateInput(property)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		} else {
			propertyService.add(property);
			return propertyService.matchPropertyWithRequirements(property);
		}
	}

	@GetMapping(path = "/bulkAdd")
	public @ResponseBody String bulkAddProperties() {
		try {
			int minPrice = 0, maxPrice = 10000;
			int minRooms = 0, maxRooms = 8;
			int counter = 0;

			JsonFactory jsonfactory = new JsonFactory();
			
			ClassPathResource classPathResource = new ClassPathResource("json/cities.json");
			InputStream inputStream = classPathResource.getInputStream();

			JsonParser parser = jsonfactory.createParser(inputStream);

			ArrayList<Property> lstProperty = new ArrayList<Property>();
			Random random = new Random();

			while (parser.nextToken() != JsonToken.END_ARRAY) {
				String token = parser.getCurrentName();

				if ("lat".equals(token)) {
					Property property = new Property();
					property.setType(random.nextBoolean() ? 1 : 0);

					parser.nextToken();
					property.setLatitude(Double.parseDouble(parser.getText()));
					parser.nextToken();
					parser.nextToken();
					property.setLongitude(Double.parseDouble(parser.getText()));
					property.setPrice(random.nextInt((maxPrice - minPrice) + 1) + minPrice);
					property.setNoOfBedrooms(random.nextInt((maxRooms - minRooms) + 1) + minRooms);
					property.setNoOfBathrooms(random.nextInt((maxRooms - minRooms) + 1) + minRooms);

					lstProperty.add(property);
					counter++;

					if (counter % 50 == 0) {
						propertyService.bulkAdd(lstProperty);
						lstProperty.clear();
					}
				}
			}

			if (lstProperty.size() != 0) {
				propertyService.bulkAdd(lstProperty);
			}

			parser.close();
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		return "Populated properties";
	}

	@GetMapping(path = "/bulkAddPropertiesGeoInMemory")
	public @ResponseBody String bulkAddPropertiesGeoInMemory() {
		propertyService.bulkAddPropertiesGeoInMemory();
		return "Populated properties geo data in memory";
	}

	public boolean validateInput(Property property) {
		if (property.getType() == null || property.getLatitude() == null || property.getLongitude() == null
				|| property.getPrice() == null || property.getNoOfBedrooms() == null
				|| property.getNoOfBathrooms() == null) {
			return false;
		} else {
			return true;
		}
	}
}
