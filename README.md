<h3 align="left">Connect with me:</h3>
<p align="left">
  https://www.linkedin.com/in/uladcherap



  
  https://t.me/UladzislauC
</p>

# Easy car
This application allows you to find a car for buying or place your own advertisement for selling.






<h3 align="left">Languages and Tools:</h3>
<p align="left"> 
<a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/></a> 
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> 
<a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a> 
<a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>
<a href="https://maven.apache.org/" target="_blank" rel="noreferrer"> <img src="https://maven.apache.org/images/maven-logo-white-on-black.purevec.svg" alt="maven" width="100" height="40"/> </a>
<a href="https://flywaydb.org/" target="_blank" rel="noreferrer"> <img src="https://flywaydb.org/wp-content/uploads/2020/12/cropped-favicon.png" alt="flyway" width="40" height="40"/> </a>
<a href="https://swagger.io/" target="_blank" rel="noreferrer"> <img src="https://upload.wikimedia.org/wikipedia/commons/a/ab/Swagger-logo.png" alt="swagger" width="40" height="40"/> </a>
<a href="https://hibernate.org/" target="_blank" rel="noreferrer"> <img src="https://upload.wikimedia.org/wikipedia/commons/2/22/Hibernate_logo_a.png" alt="hibernate" width="100" height="40"/> </a>

   </p>
<h3 align="left">Authentication</h3>
Authentication provides by JWT token. For getting token you must send the json on http://localhost:8080/auth/login. Example json : 

````
{
  "name": "foo@mail.com",
  "password": "somePassword"
}
````
<h3 align="left">Data storage</h3>
For storage the data in application, it uses PostgreSQL. With 6 tables: admins, advertisements, moderations, payments, users, vehicles.
 Also, there is Flyway framework, that used for creating tables and data for demonstration.
 
<h3 align="left">Docker run:</h3>
For run the application on your local machine, you need to download docker-compose.yaml. After use commands in package with downloaded file: 

````
docker-compose build
docker-compose up
````
<h3 align="left">Available next endpoints:</h3>
<h4 align="left">User</h4>

GET http://localhost:8080/users - get your own information, ROLE_USER.

PUT http://localhost:8080/users - update your information, ROLE_USER.

POST http://localhost:8080/users - register new user, public.

DELETE http://localhost:8080/users - delete user,  ROLE_USER.

PUT http://localhost:8080/users/update-password - update password, ROLE_USER.

<h4 align="left">Image</h4>


GET
 http://localhost:8080/images/{adId} - 
Get image, public.


PUT
 http://localhost:8080/images/{adId} - 
Replace image,  ROLE_USER.


POST
 http://localhost:8080/images/{adId} - 
Post new image,  ROLE_USER.


DELETE
 http://localhost:8080/images/{adId} - 
Delete image,  ROLE_USER.

<h4 align="left">Advertisement</h4>



PUT
 http://localhost:8080/ads/{adId} - 
Update advertisement,  ROLE_USER.


DELETE
 http://localhost:8080/ads/{adId} - 
Delete advertisement,  ROLE_USER.


PUT
 http://localhost:8080/ads/up/{adId} - 
Up advertisement,  ROLE_USER.


GET
 http://localhost:8080/ads - 
Get all public advertisements, public.


POST
 http://localhost:8080/ads - 
Create new advertisement,  ROLE_USER.


POST
 http://localhost:8080/ads/search - 
Get advertisements by search params, public.


GET
 http://localhost:8080/ads/my-ads - 
Get advertisements of user,  ROLE_USER.


GET
 http://localhost:8080/ads/moderation - 
Get moderation of user,  ROLE_USER.


<h4 align="left">Admin</h4>


PUT
 http://localhost:8080/admin/reject-advertisement - 
Reject moderation for advertisement,  ROLE_ADMIN.


PUT
 http://localhost:8080/admin/accept-advertisement - 
Accept moderation for advertisement,  ROLE_ADMIN.


POST
 http://localhost:8080/admin/add - 
Register new admin,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/payments/{userId} - 
Get payments of user,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/moderation - 
Get all moderation,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/moderation/{userId} - 
Get moderation by user,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/get-user/{userId} - 
Get inner user,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/advertisements - 
Get advertisements for moderation,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/advertisements/{userId} - 
Get advertisements of user,  ROLE_ADMIN.


GET
 http://localhost:8080/admin/advertisement/{adId} - 
Get inner advertisement,  ROLE_ADMIN.


DELETE
 http://localhost:8080/admin/user - 
Delete user,  ROLE_ADMIN.


DELETE
 http://localhost:8080/admin/image/{adId}/{imageUUID} - 
Delete image,  ROLE_ADMIN.


DELETE
 http://localhost:8080/admin/advertisement - 
Delete advertisement,  ROLE_ADMIN.

<h4 align="left">Verify</h4>




POST
 http://localhost:8080/verify/{verifyType} - 
Get code,  ROLE_USER.


GET
 http://localhost:8080/verify/{code} - 
Send code, public.

<h4 align="left">Payment</h4>



GET
 http://localhost:8080/pays - 
Get payments of user, ROLE_USER.


POST
 http://localhost:8080/pays - 
Deposit ups on account, ROLE_ADMIN.


POST
 http://localhost:8080/pays/get-token-for-demonstration - 
Get token for deposit, ROLE_ADMIN.


<h4 align="left">JWT auth</h4>

POST
 http://localhost:8080/auth/login - 
Get JWT token for user, public.


POST
 http://localhost:8080/auth/admin/login - 
Get JWT token for admin, public.
