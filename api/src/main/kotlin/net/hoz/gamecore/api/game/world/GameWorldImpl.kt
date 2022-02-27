package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoGameWorld

internal data class GameWorldImpl(
    private val arenaWorld: WorldData,
    private val lobbyWorld: WorldData,
    private val customWorlds: Map<String, WorldData>
) : GameWorld {

    override fun arenaWorld(): WorldData {
        return arenaWorld
    }

    override fun lobbyWorld(): WorldData {
        return lobbyWorld
    }

    override fun customWorlds(): Map<String, WorldData> {
        return customWorlds
    }

    override fun customWorld(name: String?): WorldData? {
        return customWorlds[name]
    }

    override fun toBuilder(builder: GameWorld.Builder.() -> Unit): GameWorld.Builder {
        return BuilderImpl(arenaWorld, lobbyWorld, customWorlds.toMutableMap())
    }

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
        private var arenaWorld: WorldData? = null,
        private var lobbyWorld: WorldData? = null,
        private var customWorlds: MutableMap<String, WorldData> = mutableMapOf()
    ) : GameWorld.Builder {

        override fun arenaWorld(): WorldData? {
            return arenaWorld
        }

        override fun arenaWorld(data: WorldData) {
            arenaWorld = data
        }

        override fun lobbyWorld(): WorldData? {
            return lobbyWorld
        }

        override fun lobbyWorld(data: WorldData) {
            lobbyWorld = data
        }

        override fun addWorld(name: String, data: WorldData) {
            customWorlds[name] = data
        }

        override fun removeWorld(name: String): Boolean {
            return customWorlds.remove(name) != null
        }

        override fun customWorlds(): Map<String, WorldData> {
            return customWorlds
        }

        override fun customWorld(name: String): WorldData? {
            return customWorlds[name]
        }

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