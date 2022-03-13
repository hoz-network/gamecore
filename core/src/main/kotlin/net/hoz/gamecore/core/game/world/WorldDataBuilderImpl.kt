package net.hoz.gamecore.core.game.world

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.game.world.WorldData
import net.hoz.gamecore.api.game.world.WorldDataBuilder
import net.hoz.gamecore.api.game.world.WorldRegenerator
import org.screamingsandals.lib.world.LocationHolder

class WorldDataBuilderImpl(
    type: ProtoWorldData.WorldType,
    regenerator: WorldRegenerator = WorldRegenerator.regenerator(),
    border1: LocationHolder? = null,
    border2: LocationHolder? = null,
    spawn: LocationHolder? = null,
    spectator: LocationHolder? = null
) : WorldDataBuilder(type, regenerator, border1, border2, spawn, spectator) {
    override fun build(): DataResultable<WorldData> {
        if (border1?.world?.uuid != border2?.world?.uuid) {
            return DataResultable.fail("Worlds are different for border1 and border2")
        }

        return DataResultable.ok(
            WorldDataImpl(
                regenerator,
                type,
                border1 ?: return DataResultable.fail("Border1 is not defined"),
                border2 ?: return DataResultable.fail("Border2 is not defined"),
                spawn ?: return DataResultable.fail("Spawn is not defined"),
                spectator
            )
        )
    }
}