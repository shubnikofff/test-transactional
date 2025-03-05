# Description

The goal of project is to test database transactions in different levels of isolation under high load and concurrency

# How to


### Run dependencies
```shell
docker compose up -d
```

### Run service
```shell
mvn spring-boot:run
```

### Run load tests
```shell
mvn gatling:test
```
