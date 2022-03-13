package net.hoz.gamecore.api.game.world

import net.hoz.gamecore.api.Buildable

abstract class GameWorldBuilder(
    protected var arenaWorld: WorldDataBuilder,
    protected var lobbyWorld: WorldDataBuilder,
    var customWorlds: MutableMap<String, WorldData> = mutableMapOf()
) : Buildable.Builder<GameWorld> {

    abstract fun arena(block: WorldDataBuilder.() -> Unit)

    abstract fun lobby(block: WorldDataBuilder.() -> Unit)
}