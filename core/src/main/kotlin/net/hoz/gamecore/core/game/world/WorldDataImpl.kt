package net.hoz.gamecore.core.game.world

import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.game.world.WorldData
import net.hoz.gamecore.api.game.world.WorldDataBuilder
import net.hoz.gamecore.api.game.world.WorldRegenerator
import org.screamingsandals.lib.world.LocationHolder

/**
 * Implementation of the [WorldData].
 */
internal data class WorldDataImpl(
    override val regenerator: WorldRegenerator,
    override val type: ProtoWorldData.WorldType,
    override val border1: LocationHolder,
    override val border2: LocationHolder,
    override val spawn: LocationHolder,
    override val spectator: LocationHolder?
) : WorldData {
    override fun asProto(): ProtoWorldData {
        val builder = ProtoWorldData.newBuilder()
            .setType(type)
            .setBorder1(border1.asProto())
            .setBorder2(border2.asProto())
            .setSpawn(spawn.asProto())

        if (spectator != null) {
            builder.spectator = spectator.asProto()
        }

        return builder.build()
    }

    override fun toBuilder(builder: WorldDataBuilder.() -> Unit): WorldDataBuilder =
        WorldDataBuilderImpl(regenerator, type, border1, border2, spawn, spectator).also { builder(it) }
}