package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoWorldData
import org.screamingsandals.lib.world.LocationHolder

/**
 * Implementation of the [WorldData].
 */
internal data class WorldDataImpl(
    private val regenerator: WorldRegenerator,
    private val border1: LocationHolder,
    private val border2: LocationHolder,
    private val spawn: LocationHolder,
    private val spectator: LocationHolder?,
    private val type: ProtoWorldData.WorldType
) : WorldData {

    override fun regenerator(): WorldRegenerator {
        return regenerator
    }

    override fun border1(): LocationHolder {
        return border1
    }

    override fun border2(): LocationHolder {
        return border2
    }

    override fun spawn(): LocationHolder {
        return spawn
    }

    override fun spectator(): LocationHolder? {
        return spectator
    }

    override fun type(): ProtoWorldData.WorldType {
        return type
    }

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

    override fun toBuilder(): DataResultable<WorldData.Builder> {
        val builder = BuilderImpl(border1, border2, spawn, spectator, type)
        builder.regenerator(regenerator)

        return DataResultable.ok(builder)
    }

    /**
     * Implementation of the [WorldData.Builder].
     */
    internal data class BuilderImpl(
        private var border1: LocationHolder? = null,
        private var border2: LocationHolder? = null,
        private var spawn: LocationHolder? = null,
        private var spectator: LocationHolder? = null,
        private var type: ProtoWorldData.WorldType? = null
    ) : WorldData.Builder {
        private var regenerator: WorldRegenerator = WorldRegenerator.regenerator()

        override fun regenerator(regenerator: WorldRegenerator) {
            this.regenerator = regenerator
        }

        override fun regenerator(): WorldRegenerator {
            return regenerator
        }

        override fun border1(): LocationHolder? {
            return border1
        }

        override fun border1(holder: LocationHolder) {
            border1 = holder
        }

        override fun border2(): LocationHolder? {
            return border2
        }

        override fun border2(holder: LocationHolder) {
            border2 = holder
        }

        override fun spawn(): LocationHolder? {
            return spawn
        }

        override fun spawn(holder: LocationHolder) {
            spawn = holder
        }

        override fun spectator(): LocationHolder? {
            return spectator
        }

        override fun spectator(holder: LocationHolder) {
            spectator = holder
        }

        override fun type(): ProtoWorldData.WorldType? {
            return type
        }

        override fun type(type: ProtoWorldData.WorldType) {
            this.type = type
        }

        override fun build(): DataResultable<WorldData> {
            val type = type()
            val border1 = border1()
            val border2 = border2()
            val spawn = spawn()

            if (type == null) {
                return DataResultable.fail("WorldType is not defined!")
            }
            if (border1 == null) {
                return DataResultable.fail("Border1 is not defined.")
            }
            if (border2 == null) {
                return DataResultable.fail("Border2 is not defined.")
            }
            if (spawn == null) {
                return DataResultable.fail("Spawn is not defined.")
            }


            return if (!border1.world.uuid.equals(border2.world.uuid)
            ) {
                DataResultable.fail("Worlds are different for border1 and border2.")
            } else DataResultable.ok(
                WorldDataImpl(
                    regenerator,
                    border1,
                    border2,
                    spawn,
                    spectator(),
                    type
                )
            )

        }
    }
}