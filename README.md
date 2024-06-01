# Car Rental Micro-Service Project

  A car rental java spring application with limited features. It provides endpoints for user registration, email confirmation, user authentication and provides the ability to search by features and pricing on cars.

<img src="https://i.imgur.com/EEKv1d2.png">

## Features

- User Registration: Users can sign up by providing their personal information, including first name, last name, email, and password.
- Email Confirmation: Upon registration, users receive an email with a confirmation link. Clicking the link verifies their email address and activates their account.
- User Authentication: Registered users can authenticate themselves using their email and password.
- Without any registration process, the user can perform a search through the cars in the inventory.
- ...

## Technologies Used

### Spring Framework

<ul>
  <li>Spring Boot</li>
    <ul>
      <li>MongoDB</li>
      <li>Redis</li>
      <li>Elasticsearch</li>
      <li>RabbitMq</li>
      <li>Security</li>
      <li>Mail</li>
    </ul>
  <li>Spring Cloud</li>
  <ul>
      <li>Cloud Config</li>
      <li>Gateway</li>
      <li>Resilience4j</li>
    </ul>
</ul>

### Documentation

- OpenAPI Documentation for Spring MVC
  
### Utilities

- Lombok
- MapStruct
- Jackson
- Hibernate Validator

### Security

- JWT (JSON Web Token)
- Auth0 Java JWT
- JJWT

### Frontend

- React
- Axios

## Filtering on the Cars page
<div align='center'">
  <img src="https://i.imgur.com/Zp5AEFJ.png"width="800"> 
</div>

### Filter by price
<div align='center'">
  <img src="https://i.imgur.com/N8wnvRd.png"width="800">
</div>

### Filter by Feature
<div align='center'">
  <img src="https://i.imgur.com/w6oxLSc.png"width="800">
</div>

## Login & Register Pages
<div>
  <img src="https://i.imgur.com/EbJ2xS2.png" width="500" />
  <img src="https://i.imgur.com/VypPcX9.png" width="500" />
</div>
