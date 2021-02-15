# Readme

### About
This is a maven / springboot project for smarthost gmbh interview process
Main goal is to create an interface for a hotel room management. [See for details](https://cutt.ly/se3ZPpA)



### Development 
Maven, Lombok and Spring Web utilized. For lombok issues see [https://projectlombok.org/](https://projectlombok.org/)
 

### Testing
##### run unit tests
> cd $PROJECT_ROOT_DIRECTORY

> mvn test

##### run project to start embedded tomcat server
> cd $PROJECT_ROOT_DIRECTORY

> ./mvnw spring-boot:run

##### Test REST API endpoints
After running the server with the command above
> go to http://localhost:8080/swagger-ui.html on browser. Requests can be made using ui.

##### Get API definitions
After running the server with the command above
> go to http://localhost:8080/v3/api-docs/ on browser.

### State
* Basic structure implemented. 
* Tests described in the project description (See details link above) , implemented. Tests build but fail.

* Implemented RoomService to fit the description. Unit tests pass.
* Enriched unit tests

* Controller class and add OpenAPI added
* Room value externalized as config

### Enhancements 
* Create room type beans and implement a single room allocation service using room types as generics