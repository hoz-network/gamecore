package net.hoz.gamecore.api.game.upgrade

interface Upgradeable {
    val upgrades: List<Upgrade>
    val upgradeType: Type

    fun addUpgrade(upgrade: Upgrade)
    fun removeUpgrade(upgrade: Upgrade)

    enum class Type {
        TEAM, STORE, SPAWNER, PLAYER, UNDEFINED
    }
}