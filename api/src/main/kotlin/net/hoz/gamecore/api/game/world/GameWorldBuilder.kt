package net.hoz.gamecore.api.game.world

import net.hoz.gamecore.api.Buildable

/**
 * Builder for the [WorldData].
 */
abstract class GameWorldBuilder(
    var arenaWorld: WorldDataBuilder,
    var lobbyWorld: WorldDataBuilder,
    var customWorlds: MutableMap<String, WorldData> = mutableMapOf()
) : Buildable.Builder<GameWorld> {

    abstract fun arena(block: WorldDataBuilder.() -> Unit)

    abstract fun lobby(block: WorldDataBuilder.() -> Unit)
}