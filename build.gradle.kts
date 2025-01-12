plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Ktor dependencies for HTTP requests and JSON handling
    implementation("io.ktor:ktor-client-core:2.3.4") // Core Ktor client
    implementation("io.ktor:ktor-client-cio:2.3.4") // CIO engine for HTTP
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4") // Content negotiation plugin
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4") // JSON serialization support
    implementation("ch.qos.logback:logback-classic:1.4.11")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}