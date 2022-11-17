# REST API for Assignment

Steps to Run the Project are as follows:

Step1: Clone the project from Git.

Step2: Run mvn clean compile install to generate the jar in the target folder

Step3: Run docker-compose -f compose-backends.yml up -d to build and run the 
database and spring boot application.

Step4: If the application startup is successful and database connection is 
found then the documentation of the API should be available at http://localhost:8090/swagger-ui.html#/

Step5: Next we Register an user using the API and a manager user in the database.

Step6: From the backend we Insert some Products and ProductPrices in the 
database. This allows us to use the full functionality of the API.

PS: For using the Reporting API user needs to have ROLE_MANAGER as user_role in 
the Database.

Please refer to the document How to access APIs with User Login for reference.


