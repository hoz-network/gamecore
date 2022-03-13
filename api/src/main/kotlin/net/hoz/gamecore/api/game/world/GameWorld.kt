package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoGameWorld
import net.hoz.gamecore.api.Buildable

/**
 * Contains information about all required points to run the game.
 */
interface GameWorld : ProtoWrapper<ProtoGameWorld>, Buildable<GameWorld, GameWorldBuilder> {
    /**
     * Gets a [WorldData] for arena world.
     *
     * @return [WorldData] containing data for the arena.
     */
    val arenaWorld: WorldData

    /**
     * Gets a [WorldData] for lobby world.
     *
     * @return [WorldData] containing data for the lobby.
     */
    val lobbyWorld: WorldData

    /**
     * Contains custom world data.
     *
     *
     * Key: name of the world
     *
     * @return map of current available custom worlds
     */
    val customWorlds: Map<String, WorldData>

    companion object {
        /**
         * Creates new builder with default data from [ProtoGameWorld].
         *
         * @param data input to get default data from
         * @return new builder
         */
        //fun builder(data: ProtoGameWorld): Builder {
        //    val maybeArena = WorldData.builder(data.arenaWorld).build()
        //    val maybeLobby = WorldData.builder(data.lobbyWorld).build()
        //    val maybeCustomWorlds = data.customWorldsMap
        //        .entries
        //        .associate {
        //            Pair(it.key, WorldData.builder(it.value).build())
        //        }
//
        //    val result = GroupedResultable.of(maybeArena, maybeLobby)
        //    result.merge(maybeCustomWorlds.values)
//
        //    if (result.isFail) {
        //        throw UnsupportedOperationException(result.message())
        //    }
//
        //    val customWorlds = maybeCustomWorlds
        //        .entries
        //        .associate { Pair(it.key, it.value.data()) }
        //        .toMutableMap()
//
        //    return GameWorldImpl.BuilderImpl(maybeArena.data(), maybeLobby.data(), customWorlds)
        //}

    }
}