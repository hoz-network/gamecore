import net.hoz.gamecore.gradle.Versions

dependencies {
    api("net.hoz.netapi", "client", Versions.NETAPI)

    api("cloud.commandframework", "cloud-core", Versions.CLOUD)
    api("cloud.commandframework", "cloud-kotlin-extensions", Versions.CLOUD)
    api("cloud.commandframework", "cloud-kotlin-coroutines", Versions.CLOUD)

    api("org.screamingsandals.simpleinventories", "core-common", Versions.SIMPLEINVENTORIES)
    api("org.screamingsandals.lib", "hologram-common", Versions.SANDALS)
}