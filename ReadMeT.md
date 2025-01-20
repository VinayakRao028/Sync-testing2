Spring Ecommerce REST API
CircleCI Build status Dependencies Docker Pulls


Ecommerce REST API from the Udemy course "Fullstack: Spring Boot and Angular" instructed by Chad Darby. Deployed at Heroku using CircleCI for the CI/CD procedure

Frontend application repository

See the application working here!


Installation
Docker
Just download the docker image from Dockerhub

docker pull victorgarciarubio/ecommerce-backend-spring

Docs
OpenAPI v3 documentation file is available here.

You can check the Swagger UI Rendered Version in my Swaggerhub Link


Continuous Integration
Jenkins
Created Jenkinsfile to setup the Continuous Integration with Jenkins Server. Builds the application, pass the test and builds the Docker image and pushes it to Dockerhub.

Circle CI
Created .circleci/config.yaml to build the application and perform the test. If the tests are passed, the Docker image is built and uploaded to Dockerhub.


Deployment
Heroku
Automatic deployment defined in master branch.

Procfile allows Heroku to launch the application.


Contribute
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.


License
