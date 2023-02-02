# Auth Model

### AuthN

The authentication for the application is handled using JWTs. All incoming requests must bear a Bearer Token corresponding to a user account
, generated via a password-verification.

All user accounts are granted an Role at the time of registration, based on the domain name of E-mail address provided.

``
Accounts with e-mail address on domain @shopper.com will be registered as ADMIN
``

`
All other email domains will be assigned the role of USER
`

### AuthZ

All API endpoints are protected using a Role Based Access Controlled System which determines if a route is accessible to a user.  

User Accounts can be registered with a role of `USER` using domains such as `@gmail.com` etc. on API endpoint `/api/account/register`

User Accounts with ``ADMIN`` role are granted, using email domain `@shopper.com`

## Database
`The Application uses H2 Embedded relation in-memory database.`

## API Endpoints
Detailed API Documentation listed below
- [API ERROR CONTRACT](documentation/api_error_contract.md)
- [INVENTORY API CONTRACT](documentation/product_api_contract.md)
- [CART CONTRACT](documentation/cart_api_contract.md)
- [USER ACCOUNT CONTRACT](documentation/user_api_contracts.md)


Below table lists all available endpoints with their Authorization criteria.

| HTTP Verb | Endpoints                   | Authority | Action                                                           |
|-----------|-----------------------------|-----------|------------------------------------------------------------------|
| GET       | `/api/account`              | `USER`    | Fetch account of the current user                                |
| POST      | `/api/account/register`     | `NONE`    | To register an user with username and email.                     |
| POST      | `/api/account/authenticate` | `NONE`    | To authenticate as an existing user, using username and password |
| POST      | `/api/account/suspend`      | `ADMIN`   | To suspend an user account by username                           |
| POST      | `/api/account/reinstate`    | `ADMIN`   | To reinstate an user account by username                         |
| GET       | `/api/account/all`          | `ADMIN`   | Fetch all registered user accounts.                              |
| GET       | `/api/product`              | `USER`    | Fetch all available Products                                     |
| POST      | `/api/product`              | `ADMIN`   | To create new Inventory                                          |
| PUT       | `/api/product`              | `ADMIN`   | To update existing Inventory                                     |
| POST      | `/api/product/delete`       | `ADMIN`   | Fetch all created Inventory.                                     |
| GET       | `/api/product/all`          | `ADMIN`   | Fetch all created Inventory.                                     |
| GET       | `/api/cart   `              | `USER`    | Fetch cart of the logged in user.                                |
| POST      | `/api/cart`                 | `USER`    | To add/update a product into the cart of logged in user.         |
| GET       | `/api/api/cart/all    `     | `ADMIN`   | Fetch all existing carts.                                        |
| POST      | `/api/cart/delete`          | `USER`    | Delete an existing product from logged in user's cart.           |








### Dependencies
```agsl
Java 17.0.6
Apache Maven 3.8.7
```


## Testing 

The APIs can be tested by running the spring application, by building and executing the project ```jar```.

Below steps describe how to build the spring project.
### Building Spring Project
To build the Spring Project, execute the following command.

Ensure that all the required dependencies (JDK17, and Maven) are installed.
```agsl
> mvn clean package
```

If the build succeeds,  a `jar` file in `target` directory should be created.

`Example : cartapp-0.0.1-SNAPSHOT.jar`

If the build succedes, you can proceed to next step, i.e. starting the web server

### Starting the web server
To start the webserver, execute the jar, by running the following
    
```agsl
> java -jar cartapp-0.0.1-SNAPSHOT.jar
```

A  Tomcat Server should spin up and start listening for requests on port `8080` .

## Test Using Docker

The application can also be run by pulling the docker image available [here](https://drive.google.com/drive/folders/1a1e8q1U6Oz9-2LNg6-XiVdSN4WvZ52Eo?usp=share_link)

Please download the above docker image, and run

`
docker run -p 8080:8080 cartapp
`
