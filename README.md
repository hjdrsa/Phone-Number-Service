
# phone-number-service
A service to manage connectivity of phone number to customers

## Prerequisites
 - Git client
 - Java 11
 - Maven version 3.5.x or later
 - Java IDE
 - Docker for Desktop (Optional)

## Getting Started

To get the project up and running on your local machine, do the following:
 
 1. Check out the phone-number-service project code using Git.
 2. Go to the root directory of the checked out phone-number-service project.
 3. Run `mvn package`
 4. Run `java -jar target/phone-number-service-1.0.0.jar`
 5. Navigate to [http://localhost:15000/swagger-ui.html](http://localhost:15000/swagger-ui.html)

### Testing overview

 6. Go to the following directory from your project root `\target\site\jacoco`
 7. Click on `index.html`

## Getting Started With Docker

To get the project up and running on your local machine, do the following:

 1. Check out the phone-number-service project code using Git.
 2. Go to the root directory of the checked out phone-number-service project.
 3. Run `mvn package fabric8:build`
 4. Run `docker run --name phone-number-service -d -p 15000:15000 phone-number-service:1.0.0`
 5. Wait for the components to be up and running.
 6. Navigate to [http://localhost:15000/swagger-ui.html](http://localhost:15000/swagger-ui.html)