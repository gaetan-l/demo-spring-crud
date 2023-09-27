# Spring bootless persistence

Basic persistence tutorial for beginners, using Spring but not Boot and minimal dependencies.

- Java language
- Spring framework
- Gradle for dependency management
- Dependencies:
  - JPA
  - Hibernate
  - H2
  - Log4j
  - JUnit
  - Spring test

## How to use

1. Clone git project
2. Read code comments for explanations
3. Spring magic happens with MessageRepository, which, by extending (List)CrudRepository implicitly implements all methods needed for CRUD operations
4. MessageService is using MessageRepository but its methods are meant to be exposed to public use (e.g. in an API)
5. Application class can be run, code can be added in main() to try the various CRUD operations
6. MessageServiceTest can be run as well for testing. Database access would be mocked in a real application but this was not the goal of this demo
7. The database is configured in "create-drop" mode, meaning it's created at the beginning and dropped at the end of each execution (it doesn't keep data between executions). This setting can be changed in src/main/resources/hibernate.cfg.xml