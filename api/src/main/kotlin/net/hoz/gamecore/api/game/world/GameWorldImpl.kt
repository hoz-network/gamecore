package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoGameWorld

internal data class  GameWorldImpl(
    override val arenaWorld: WorldData,
    override val lobbyWorld: WorldData,
    override val customWorlds: Map<String, WorldData>
) : GameWorld {
    override fun toBuilder(builder: GameWorld.Builder.() -> Unit): GameWorld.Builder =
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

    internal data class BuilderImpl(
        override var arenaWorld: WorldData? = null,
        override var lobbyWorld: WorldData? = null,
        override var customWorlds: MutableMap<String, WorldData> = mutableMapOf()
    ) : GameWorld.Builder {
        override fun build(): DataResultable<GameWorld> {
            val arenaWorld = this.arenaWorld
            val lobbyWorld = this.lobbyWorld

            if (arenaWorld == null) {
                return DataResultable.fail("ArenaWorld is not defined!")
            }
            if (lobbyWorld == null) {
                return DataResultable.fail("lobbyWorld is not defined!")
            }

            return DataResultable.ok(GameWorldImpl(arenaWorld, lobbyWorld, customWorlds))
        }
    }
}