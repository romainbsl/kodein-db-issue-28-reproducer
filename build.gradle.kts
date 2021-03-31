import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    application
}

group = "org.kodein.issue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.kodein.db:kodein-db:0.6.1-beta")
    implementation("org.kodein.db:kodein-db-serializer-kotlinx:0.6.1-beta")
    implementation("org.kodein.db:kodein-leveldb-jni-jvm-windows:0.6.1-beta")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")


    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        useIR = true
    }
}