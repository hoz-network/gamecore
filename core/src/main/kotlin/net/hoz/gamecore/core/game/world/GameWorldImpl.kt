package net.hoz.gamecore.core.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.gamecore.api.game.world.GameWorld
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.game.world.WorldData

internal data class GameWorldImpl(
    override val arenaWorld: WorldData,
    override val lobbyWorld: WorldData,
    override val customWorlds: Map<String, WorldData>
) : GameWorld {
    override fun toBuilder(builder: GameWorldBuilder.() -> Unit): GameWorldBuilder =
        BuilderImpl(arenaWorld, lobbyWorld, customWorlds.toMutableMap())

    override fun asProto(): ProtoGameWorld {
        val worlds = customWorlds.entries
            .associate { Pair(it.key, it.value.asProto()) }

        return ProtoGameWorld.newBuilder()
            .setArenaWorld(arenaWorld.asProto())
            .setLobbyWorld(lobbyWorld.asProto())
            .putAllCustomWorlds(worlds)
            .build()
    }
}