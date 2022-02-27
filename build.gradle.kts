plugins {
    kotlin("jvm") version "1.6.20-M1" apply false

    id("org.screamingsandals.plugin-builder") version "1.0.76"
}

allprojects {
    group = "net.hoz.gamecore"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    apply {
        plugin("java-library")
        plugin("idea")
        plugin("org.screamingsandals.plugin-builder")
    }

    repositories {
        mavenCentral()
        maven(url = "https://repo.hoznet.dev/snapshots")
        maven(url = "https://repo.screamingsandals.org/public")
    }

    dependencies {
        implementation("io.github.microutils", "kotlin-logging-jvm", "2.1.20")
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
}