pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://repo.screamingsandals.org/public/")
    }
}

rootProject.name = "kgamecore"

include("api")
include("core")

