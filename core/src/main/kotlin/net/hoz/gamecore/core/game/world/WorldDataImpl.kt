package net.hoz.gamecore.core.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoWorldData
import org.screamingsandals.lib.world.LocationHolder

/**
 * Implementation of the [WorldData].
 */
internal data class WorldDataImpl(
    override val regenerator: WorldRegenerator,
    override val border1: LocationHolder,
    override val border2: LocationHolder,
    override val spawn: LocationHolder,
    override val spectator: LocationHolder?,
    override val type: ProtoWorldData.WorldType
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

    override fun toBuilder(builder: WorldData.Builder.() -> Unit): WorldData.Builder =
        BuilderImpl(regenerator, border1, border2, spawn, spectator, type).also { builder(it) }

    /**
     * Implementation of the [WorldData.Builder].
     */
    internal data class BuilderImpl(
        override var regenerator: WorldRegenerator = WorldRegenerator.regenerator(),
        override var border1: LocationHolder? = null,
        override var border2: LocationHolder? = null,
        override var spawn: LocationHolder? = null,
        override var spectator: LocationHolder? = null,
        override var type: ProtoWorldData.WorldType? = null
    ) : WorldData.Builder {
        override fun build(): DataResultable<WorldData> {
            if (border1?.world?.uuid != border2?.world?.uuid) {
                return DataResultable.fail("Worlds are different for border1 and border2")
            }

            return DataResultable.ok(
                WorldDataImpl(
                    regenerator,
                    border1 ?: return DataResultable.fail("Border1 is not defined"),
                    border2 ?: return DataResultable.fail("Border2 is not defined"),
                    spawn ?: return DataResultable.fail("Spawn is not defined"),
                    spectator,
                    type ?: return DataResultable.fail("WorldType is not defined")
                )
            )
        }
    }
}