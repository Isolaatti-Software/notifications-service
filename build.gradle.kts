plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.isolaatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.rabbitmq:amqp-client:5.18.0")
    implementation("com.google.firebase:firebase-admin:9.2.0")
    implementation("com.sendgrid:sendgrid-java:4.9.3")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.apache.commons:commons-dbcp2:2.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}