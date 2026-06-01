Employee Payroll App:

A simple Employee Payroll Management REST API built using Spring Boot, Spring MVC, Spring Data JPA, Spring JDBC, Dependency Injection, and MySQL.



Features:

Add Employee

View All Employees

View Employee By ID

Delete Employee



Technologies Used

Java 17

Spring Boot

Spring MVC

Spring Data JPA

Spring JDBC

MySQL

Maven

Postman



Application will start on:

http://localhost:8080

Add Employee

POST:http://localhost:8080/employees

Request Body:

Ex:

{

&#x20; "name": "Pabitra",

&#x20; "department": "IT",

&#x20; "salary": 50000

}

Get All Employees

GET:http://localhost:8080/employees

Get Employee By ID

GET:http://localhost:8080/employees/{id}

Example:

http://localhost:8080/employees/1

Delete Employee

DELETE:http://localhost:8080/employees/{id}

Example:

http://localhost:8080/employees/1

