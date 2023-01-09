
# WhiteBox

- Exercise File contains two projects i.e  (1). api-gateway project. (2). bank-account project.

-- Build and install all Projects by using mvn clean install

    a.   api-gateway is Spring boot application. An API gateway is rest api that sits in front of an API (Application Programming Interface) and serves as a single point of access for microservices and back-end APIs that are specified.

    1. bankcc.core  (Its a plan java project contains models, events, base Dtos)
    2. bankcc.cmd.api (Its a command api. It is used to create an account and execute a payment )
    3. bankcc.query.api (Its a query api. Its used to query the accounts and transactions )

-


    1. Run the docker-compose file by using command "docker-compose up".
    2  Start api-gateway spring boot project.
    3. Start bankcc.cmd.api spring boot project.
    4. Start bankcc.query.api spring boot project.


- Open postman and add the collections (whitebox bank.postman_collection.json)

- Run each request in postman 

- Open a browser for axion dashboard http://localhost:8024/ 

- verify the MySQL Database if all the records are persisted correctly.

- For reference please check the document (Whitebox-event sourcing and CQRS.docx) attached in project
