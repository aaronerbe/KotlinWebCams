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

// Set version as variable to simplify updating later if needed
val ktor_version = "3.0.3"

dependencies {
    //?? was here by default
    testImplementation(kotlin("test"))
    //core ktor dependency
    implementation("io.ktor:ktor-client-core:$ktor_version")
    //engine dependency (CIO) which handles processing network requests
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    //logging plugin
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    //content negotiation and serialization.  to handle json response
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    //for logging.  removes console warnings about logging to enable this.
        implementation("ch.qos.logback:logback-classic:1.4.12")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}