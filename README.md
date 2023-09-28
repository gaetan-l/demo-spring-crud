# Minimal persistence demo with Spring (without Boot)

Minimal code to demonstrate persistence using Spring witout Boot and minimal dependencies.

- Java 20
- [IntelliJ Idea 2023.2.2 Community Edition](https://www.jetbrains.com/idea/download/)
- Gradle 8.3 (Version 8.3 is required to work with Java 20, by default, ItelliJ included version 8.2, to upgrade to 8.3, use the following command in a terminal: `.\gradlew wrapper --gradle-version 8.3`)
- Dependencies
  - [org.springframework.data:spring-data-jpa:3.1.4](https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa)
  - [org.hibernate.orm:hibernate-community-dialects:6.3.0.Final](https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects)
  - [org.hibernate:hibernate-entitymanager:5.6.15.Final](https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager)
  - [com.h2database:h2:2.2.224](https://mvnrepository.com/artifact/com.h2database/h2)
  - [org.slf4j:slf4j-log4j12:2.0.9](https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12)
  - [org.junit.jupiter:junit-jupiter:5.9.2](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter)
  - [org.springframework:spring-test:6.0.12](https://mvnrepository.com/artifact/org.springframework/spring-test)


## How to use

1. Clone git project
2. Read code comments for explanations
3. Spring magic happens with `MessageRepository`, which by extending `(List)CrudRepository` implicitly implements all methods needed for CRUD operations
4. `MessageService` is using `MessageRepository` but its methods are meant to be exposed to public use (e.g. in an API)
5. `Application` class can be run, code can be added in `main()` to try the various CRUD operations
6. `MessageServiceTest` can be run as well for testing. Database access would be mocked in a real application but this was not the goal of this demo
7. The database is configured in "create-drop" mode, meaning it's created at the beginning and dropped at the end of each execution (it doesn't keep data between executions). This setting can be changed in `src/main/resources/hibernate.cfg.xml`