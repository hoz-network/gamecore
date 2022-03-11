package net.hoz.gamecore.core.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.world.GameWorld
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.game.world.WorldData
import net.hoz.gamecore.api.game.world.WorldDataBuilder

class GameWorldBuilderImpl(
    arenaWorld: WorldDataBuilder?,
    lobbyWorld: WorldDataBuilder?,
    customWorlds: MutableMap<String, WorldData> = mutableMapOf()
) : GameWorldBuilder(arenaWorld, lobbyWorld, customWorlds) {

    override fun build(): DataResultable<GameWorld> {
        val builtArenaWorld = this.arenaWorld?.build()
            ?: return DataResultable.fail("ArenaWorld is not defined!")
        val builtLobbyWorld = this.lobbyWorld?.build()
            ?: return DataResultable.fail("LobbyWorld is not defined!")

        if (builtArenaWorld.isFail) {
            return builtArenaWorld.transform()
        }
        if (builtLobbyWorld.isFail) {
            return builtLobbyWorld.transform()
        }

        val world =
            GameWorldImpl(
                builtArenaWorld.data(),
                builtLobbyWorld.data(),
                customWorlds
            )
        return DataResultable.ok(world)
    }
}