plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    //Configures repositories for ktor
    mavenCentral()
}

val ktor_version = "3.0.3"

dependencies {
    testImplementation(kotlin("test"))
    //core ktor dependency
    implementation("io.ktor:ktor-client-core:$ktor_version")
    //engine dependency (CIO) which handles processing network requests
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    //logging plugin
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    //content negotiation and serialization.  to handle json reponse
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    //for logging.  removes console errors about logging to enable this.  not necessary
    //    implementation("ch.qos.logback:logback-classic:1.4.11")

//    // Ktor dependencies for HTTP requests and JSON handling
//    implementation("io.ktor:ktor-client-core:2.3.4") // Core Ktor client
//    implementation("io.ktor:ktor-client-cio:2.3.4") // CIO engine for HTTP
//    implementation("io.ktor:ktor-client-content-negotiation:2.3.4") // Content negotiation plugin
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4") // JSON serialization support
//    implementation("ch.qos.logback:logback-classic:1.4.11")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}