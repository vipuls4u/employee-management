###EMPLOYEE CRUD Operation
spring boot2 jpa crud REST APIs

###Swagger UI
http://localhost:8081/swagger-ui.html
###OPen Issue - date format for  "dateOfJoining": "2022-10-22T15:57:38.132Z" use below format for PUT and POST
 {
"empName": "Dollie R. Schn",
"emailId": "Dollie R. Schn@testCompany.com",
#####"dateOfJoining": "03-02-2022 00:00",
"salary": 84264.52,
"country": "UK"
}

###IN MEMORY DB
	 * Creates an in-memory "rewards" database populated with test data for fast testing	 

##END POINTS#####

####Get ALL EMPLOYEE -GET
http://localhost:8081/api/v1/employees

####GET EMPLOYEE BY ID -GET
http://localhost:8081/api/v1/employees/2

#####CREATE -POST Method
http://localhost:8081/api/v1/employees

Request Body: 
{
"empName": "Dollie R. Schndee",
"emailId": "Dollie R. Schn@testCompany.com",
"dateOfJoining": "02-09-2022 23:00",
"salary": 70639.76,
"country": "IND"
}

#####UPDATE - PUT Method
http://localhost:8081/api/v1/employees/1
Request Body:
{
"empName": "Dollie R. Schndee",
"emailId": "Dollie R. Schn@testCompany.com",
"dateOfJoining": "02-09-2022 23:00",
"salary": 70639.76,
"country": "IND"
}

####DELETE  - DELETE
http://localhost:8081/api/v1/employees/1


#### Apply 5% Bonus for employee SAL>50K Joined- this year and Country UK
#####GET Method
http://localhost:8081/api/v1/employees/bonus


