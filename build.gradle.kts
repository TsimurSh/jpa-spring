import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    jacoco
    val kotlinVersion = "1.9.23"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("com.github.ben-manes.versions") version "0.48.0"
}

repositories {
    mavenLocal(); mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.liquibase:liquibase-core")


    implementation("org.springdoc:springdoc-openapi-ui:1.6.7")

    runtimeOnly("com.h2database", "h2")
    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot", "spring-boot-devtools")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test:")
}

group = "pl.tsimur"
version = "0.0.1-SNAPSHOT"
description = "jpa-spring"

java.sourceCompatibility = JavaVersion.VERSION_17

tasks {
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "17" } }
    withType<Test> { useJUnitPlatform() }
}
