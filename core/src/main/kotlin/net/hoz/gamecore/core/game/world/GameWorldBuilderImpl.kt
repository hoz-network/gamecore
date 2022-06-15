package net.hoz.gamecore.core.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.api.data.game.ProtoWorldData.WorldType
import net.hoz.gamecore.api.game.world.GameWorld
import net.hoz.gamecore.api.game.world.GameWorldBuilder
import net.hoz.gamecore.api.game.world.WorldData
import net.hoz.gamecore.api.game.world.WorldDataBuilder

class GameWorldBuilderImpl(
    arenaWorld: WorldDataBuilder = WorldDataBuilderImpl(WorldType.ARENA),
    lobbyWorld: WorldDataBuilder = WorldDataBuilderImpl(WorldType.LOBBY),
    customWorlds: MutableMap<String, WorldData> = mutableMapOf()
) : GameWorldBuilder(arenaWorld, lobbyWorld, customWorlds) {

    companion object {
        fun fromProto(data: ProtoGameWorld): GameWorldBuilder {
            val customWorlds = data.customWorldsMap
                .mapValues { WorldDataBuilderImpl.fromProto(it.value).build() }
                .filter { it.value.isOk }
                .mapValues { it.value.data() }
                .toMutableMap()

            return GameWorldBuilderImpl(
                WorldDataBuilderImpl.fromProto(data.arenaWorld),
                WorldDataBuilderImpl.fromProto(data.lobbyWorld),
                customWorlds
            )
        }
    }

    override fun arena(block: WorldDataBuilder.() -> Unit) {
        arenaWorld.block()
    }

    override fun lobby(block: WorldDataBuilder.() -> Unit) {
        lobbyWorld.block()
    }

    override fun build(): DataResultable<GameWorld> {
        val builtArenaWorld = this.arenaWorld.build()
        val builtLobbyWorld = this.lobbyWorld.build()

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