plugins {
    java
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "com.meniadin.RMQ.RMQRunner"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
