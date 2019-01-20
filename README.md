# property-match
Property Match is a back end application which matches properties based on search requirements and matches search requirements posted by agents based on a property.  

So when a new property gets added, we return the list of matched search requirements with a match percentage and when new search requirement gets added, we return the list of matched properties with the match percentage.


## Technologies used
Its a Java Spring Boot application and it uses Spring Data for queries to MySQL.

MySQL is used as persistent storage to store properties and search requirements posted by agents. Redis is the in-memory database used. Redis has georadius feature which calculates the distances between locations. It assumes earth as a sphere for calculating distances.

Java - 1.8.0_05
Spring - 2.1.2.RELEASE
Spring Data Redis - 2.14.RELEASE
MySQL - 8.0.13 


Pre-requisites to run the code:
------------------------------
1) Git, maven and java must be installed.  
2) Following environment variables must be set with database name set to "radius" in mysql db url. For example as follows:
	1. MYSQL_DB_URL : jdbc:mysql://localhost:3307/radius?autoReconnect=true&useSSL=false&rewriteBatchedStatements=true
	2. MYSQL_DB_USERNAME : username
	3. MYSQL_DB_PASSWORD : password
	4. REDIS_HOST : 127.0.0.1
	5. REDIS_PORT : 6379
3) Create schema named "radius" in your database.

## Steps to run the code
1. Clone the code from git repository using below command.  
   git clone --branch develop https://github.com/pooja-ps/property-match.git
2. cd property-match
3. mvn package
4. java -jar target/property-match-1.jar

## Seed data population
1. To populate properties seed data in table, we are using cities.json for geo information and other fields are generated randomly.  
Invoke the URL http://localhost:9000/property/bulkAdd to populate the table.
2. Similarly populate seed date for search requirements in table by invoking the URL http://localhost:9000/searchrequirement/bulkAdd.  
3. To populate the properties geo info in redis in-memory db, invoke the URL
http://localhost:9000/property/bulkAddPropertiesGeoInMemory
4. Similarly to populate the search requirements geo info in redis in-memory db, invoke the URL http://localhost:9000/searchrequirement/bulkAddRequirementsGeoInMemory

## APIs to post property or search requirement and get the relevant matches

To add property and get relevant search requirement matches, invoke the URL http://localhost:9000/property/add .  Sample request is as follows:

{

	"type":0,
	"latitude":74.001010,
	"longitude":40.1028029,
	"price":"3446",
	"noOfBathrooms":3,
	"noOfBedrooms":1
}
        
Here for type field - 0 indicates rent and 1 indicates sale type of property


To add search requirement and get relevant properties in result, invoke the URL
http://localhost:9000/searchrequirement/add . Sample request is as follows:

{  

	"type":0,
	"latitude":76.001010,
	"longitude":40.1028029,
	"minBudget":"3526",
	"maxBudget":"4000",
	"minBedrooms":null,
	"maxBedrooms":4,
	"minBathrooms":null,
	"maxBathrooms":4
}
	
    
## What happens internally?

When post request is made to http://localhost:9000/property/add:
1. The request gets validated first
2. Then we create an entry in MySQL DB and 
3. Then add an entry in Redis for the key named "Property" with longitude, latitude and the id of that property got from DB using add method provided by Redis.
4. Next, for that property we get all the matching search requirements posted by other agents from Redis using the radius method provided by Redis.  Here we specify to get search requirements which are within 10 miles from the property which just got added.
5. For each of the search requirements we got in the previous step we check if its type is same as the property's type (sale/rental) and then compute match percentage
6. In the final result set we only include those requirements whose match percentage is at-least 40% 

When we post search requirement, similar steps mentioned above are followed except that now an entry gets created in Redis with the key named "Requirement" and here we search for properties using the radius method and compute match percentage and filter based on it.


## To compute match percentage

1. Based on Distance

We consider only matches which are within 10 miles.  If the distance is within 2 miles, distance contribution for the match percentage is fully 30%.    As the distance increases the match percentage decreases.

2. Based on Budget

If the budget is within min and max budget, budget contribution for the match percentage is full 30%. If either min or max - i.e., one of them is provided and if the price is within +/- 10% then its a full match. In case both are provided but if the price is not within it but its with within +/- 10% then here we have assumed it to be a full match. If difference is greater than 25% then match percentage is 0.  In all the other cases we get the diff and check if its within 25% and if so then calculate the match percentage based on the diff.

3. Based on Bedroom and Bathroom

Both have similar calculations. If bedroom and bathroom fall between min and max, each will contribute full 20% (maxRoomPercentage)
else we have assumed that if the difference is 1, match percentage of room is maxRoomPercentage/2 i.e., 10% 
and if difference is 2, match percentage of room is maxRoomPercentage/4 i.e., 5% and if difference is > 2 then its 0





