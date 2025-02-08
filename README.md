# product-app
SpringBoot microservice with redis cache and Postgres database backed with docker compose 

How to build and run
1. clone the repository
2. Go to the project folder and perform the maven build: ./mvnw clean package
3. Run docker compose: docker compose up --build
4. Check the endpoint: curl -i http://localhost:8090/api/v1/products
5. Once done bring down the whole container : docker compose stop
6. Can remove the images : docker compose down
