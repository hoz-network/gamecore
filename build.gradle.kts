plugins {
    kotlin("jvm") version "1.6.20-M1" apply false

    id("org.screamingsandals.plugin-builder") version "1.0.76"
    jacoco
}

allprojects {
    group = "net.hoz.gamecore"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    apply {
        plugin("java-library")
        plugin("idea")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.screamingsandals.plugin-builder")
        plugin("org.gradle.jacoco")
    }

    repositories {
        mavenCentral()
        maven("https://repo.hoznet.dev/snapshots")
        maven("https://repo.screamingsandals.org/public")
    }

    dependencies {
        implementation("io.github.microutils", "kotlin-logging-jvm", "2.1.20")

        testImplementation("org.slf4j", "slf4j-simple", "1.7.36")
        testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.8.2")
        testImplementation("org.mockito.kotlin", "mockito-kotlin", "4.0.0") {
            constraints {
                // bump mockito-core transitive dependency
                testImplementation("org.mockito", "mockito-core", "4.4.0")
            }
        }
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
    }
    tasks.jacocoTestReport {
        dependsOn(tasks.test)
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    }
}