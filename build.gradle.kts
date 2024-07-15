plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.nickcoblentz.montoya"
version = "0.1"

repositories {
    mavenLocal()
    mavenCentral()
    maven(url="https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.portswigger.burp.extensions:montoya-api:2023.12.1")
    implementation("com.nickcoblentz.montoya:MontoyaLibrary:0.1.12")
    implementation("com.github.milchreis:uibooster:1.21.1")
    implementation("org.json:json:20240303")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}