package net.hoz.gamecore.api.game.world

import net.hoz.gamecore.api.Buildable

abstract class GameWorldBuilder(
    var arenaWorld: WorldDataBuilder?,
    var lobbyWorld: WorldDataBuilder?,
    var customWorlds: MutableMap<String, WorldData> = mutableMapOf()
): Buildable.Builder<GameWorld>