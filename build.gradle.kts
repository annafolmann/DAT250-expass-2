plugins {
    java
    application
    // id("org.springframework.boot") version "3.5.5"
    // id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.1.1.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("com.h2database:h2:2.3.232")
    implementation("redis.clients:jedis:6.2.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("redis.clients:jedis:5.1.0")

}

application {
    mainClass.set("DAT250.RedisExperiment")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

