plugins {
    id("java")
}

group = "me.ledovec"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.ghgande:j2mod:3.1.1")
}

tasks.test {
    useJUnitPlatform()
}