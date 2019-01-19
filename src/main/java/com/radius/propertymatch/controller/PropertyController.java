package com.radius.propertymatch.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
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
@RequestMapping(path="/property")
public class PropertyController {
	
	@Autowired
	IPropertyService propertyService;

	@PostMapping(path="/add", produces="application/json", consumes="application/json")
	public @ResponseBody List<SearchRequirement> addProperty(@RequestBody Property property){
		propertyService.add(property);
		return propertyService.matchPropertyWithRequirements(property);		
	}
	
	@GetMapping(path="/bulkadd")
	public @ResponseBody String bulkAddProperties() {		
		try {
			int minPrice = 0, maxPrice = 10000;
            int minRooms = 0, maxRooms = 8;
            int counter = 0;
			
            JsonFactory jsonfactory = new JsonFactory();
            File source = ResourceUtils.getFile("classpath:json/cities.json");

            JsonParser parser = jsonfactory.createParser(source);            

            ArrayList<Property> lstProperty = new ArrayList<Property>();
            Random random = new Random();
            
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                String token = parser.getCurrentName();                
                
                if ("lat".equals(token)) {
                    Property property = new Property();        
                   	property.setType(random.nextBoolean()?1:0);

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
            
            if(lstProperty.size()!=0) {
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
	
}
