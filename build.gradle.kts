plugins {
    id("java")
}

group = "com.cypherf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Persistence
        // JPA
        implementation("org.springframework.data:spring-data-jpa:3.1.4")

        // Hibernate
        implementation("org.hibernate.orm:hibernate-community-dialects:6.3.0.Final")
        implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")


    // H2
        implementation("com.h2database:h2:2.2.224")

    // Logging
    implementation("org.slf4j:slf4j-log4j12:2.0.9")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.springframework:spring-test:6.0.12")
}

tasks.test {
    useJUnitPlatform()
}