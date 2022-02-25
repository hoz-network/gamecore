package net.hoz.gamecore.api.game.upgrade

interface Upgradeable {
    fun upgradeType(): Type
    fun upgrades(): List<Upgrade>
    fun addUpgrade(upgrade: Upgrade)
    fun removeUpgrade(upgrade: Upgrade)

    enum class Type {
        TEAM, STORE, SPAWNER, PLAYER, UNDEFINED
    }
}