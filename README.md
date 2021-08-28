
## ProductStore Application

![img](/src/main/resources/static/images/productstore.gif)
## About
Application that simulates an ecommerce with the user being able to login and add products to the cart.
## Project Structure
- **Frontend**: HTML page with Thymeleaf and Bootstrap.
- **Backend**: Spring API secured with Spring Security + JWT, mostly controllers are for ADMIN usage only.
- **Tests**: Unity Tests where made to the Cart Service.
## Technologies
- Spring
- Spring Security
- JWT
- PostgreSQL
- Lombok
- Thymeleaf
- Bootstrap
- jUnit
- Mockito
- SLF4J
- Toastr
## Usage
`Only users registered by an ADMIN can use the application[1].`

The application can be accessed here (`It may take a minute to load`):

	
	https://marcusmiguel-restapi.herokuapp.com/

And the Swagger Docs here: 

	https://marcusmiguel-restapi.herokuapp.com/swagger-ui.html


[1] Already registered users:

	username: admin
	password: admin

	username: consumer
	password: consumer
