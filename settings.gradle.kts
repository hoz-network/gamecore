pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://repo.screamingsandals.org/public/")
    }
}

rootProject.name = "gamecore"

include("api")
include("core")

