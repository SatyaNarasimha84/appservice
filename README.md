# Security Question App Service 
This example project demonstrates Q & A app service using Spring Boot web application

There are only two essential things to add to any basic Spring Boot project:

# Pre-requisites
1. Java 1.8 installed
2. Maven version >=3.3.3
3. Spring  : https://start.spring.io/  
4. Eclipse IDE  


# Requirement 
	Client: Hey Service, can you provide me a question with numbers to add?
	Service: Here you go, solve the question: “Please sum the numbers 9,5,3”.
	Client: Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15.
	Service: That’s wrong. Please try again.
	Client: Sorry, the original question was “Please sum the numbers 9,5,3” and the answer is 17.
	Service: That’s great

# Analysis
  	1. Client will send request with text asking for question (add) to solve
   	2. Server will responsd with question (addition of multiple numbers) to solve
   	3. Client will read the question and sends back with Answer (total sum of numbers)
  	4. Server will read the question and Answer from User input and checks with sum of numbers and answer matches
      		if matches then sends response as "That's" Great" , response code - 200
      		if doesn't match sends response as  "That’s wrong. Please try again" ,response code - 400

# Implementation :
  The App Service Implemented using following technologies
        1. Sprin boot  for developing application
	2. Maven for dependency managemnt
	3. Swagger for Api Documentation
	4. Spring cache for cache implementation in app
	5. Junit for Unit test case
	6. Logger for logging information
   
#Authentication :  
```java
Generating JWT Token Using registered User details( Username and Password )
url     :  http://localhost:8080/authenticate
headers :  Content-Type : application/json
body	:  {"username":"satya","password":"password"} or {"username":"test","password":"Test@123"} or {"username":"admin","password":"secure@123"}

Response : 
 sampleformat : {"token":"tokenvalue"}
{"token":
"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY1MTYyMTA1OSwiaWF0IjoxNjUxNjAzMDU5fQ.u48O71VTr8iy02CUmm_gIE-fsrlZHH6CIzSnozy5HCSC7qLiaaUTRiX5V719SjF-1N_DuB71RHX6SGcI8LuEPw"}

```

# Service Application  Use Cases : 
```java

When ever you send request to service, you need to include following header(headername: headervalue) for authorization
authorization : Bearer tokenvalue
 
 Valid Use Case :
 Client  : http://localhost:8080/question?text=Hey Service, can you provide me a question with numbers to add?
 Service : Response from Service :Here You  Go solve the question: "Please sum the numbers 15,6" 
 Clinet  : http://localhost:8080/question/clientResponse?userInput=Great. The original question was "Please sum the numbers 15,6" and the answer is 21.
 Service : That's Right

 Invalid Valid Use Case :
 Client  : http://localhost:8080/question?text=Hey Service, can you provide me a question with numbers to add?
 Service : Response from Service :Here You  Go solve the question: "Please sum the numbers 15,6" 
 Clinet  : http://localhost:8080/question/clientResponse?userInput=Great. The original question was "Please sum the numbers 15,6" and the answer is 22.
 Service : That’s wrong. Please try again
   
```
    
## Swagger API 
```java
http://localhost:8080/swagger-ui/
```


## Reference Documentation
For further reference, please consider the following sections:
* [Spring] (https://start.spring.io/ )
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.6/reference/html/)

