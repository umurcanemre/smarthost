# Readme

### About
This is a maven / springboot project for smarthost gmbh interview process
Main goal is to create an interface for a hotel room management. [See for details](https://cutt.ly/se3ZPpA)

### Development 
Maven, Lombok and Spring Web utilized. For lombok issues see [https://projectlombok.org/](https://projectlombok.org/)
 

### Testing
#####run unit tests
> cd $PROJECT_ROOT_DIRECTORY

> mvn test

#####run project to start embedded tomcat server
> cd $PROJECT_ROOT_DIRECTORY

> ./mvnw spring-boot:run

### State
* Basic structure implemented. 
* Tests described in the project description (See details link above) , implemented. Tests build but fail.

* Implemented RoomService to fit the description. Unit tests pass.
* Enriched unit tests

### Planned
* Enrich unit tests with corner cases.
* Implement controller class and add OpenAPI