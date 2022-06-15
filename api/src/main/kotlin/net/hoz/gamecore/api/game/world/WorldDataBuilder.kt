package net.hoz.gamecore.api.game.world

import com.iamceph.resulter.core.Resultable
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.Buildable
import net.hoz.gamecore.api.util.GUtil
import org.screamingsandals.lib.world.LocationHolder

abstract class WorldDataBuilder(
    val type: ProtoWorldData.WorldType,
    val regenerator: WorldRegenerator,
    var border1: LocationHolder? = null,
    var border2: LocationHolder? = null,
    var spawn: LocationHolder? = null,
    var spectator: LocationHolder? = null,
) : Buildable.Builder<WorldData> {

    fun isInBorder(location: LocationHolder): Resultable {
        val border1 = this.border1
            ?: return Resultable.fail("Border 1 is not defined.")
        val border2 = this.border2
            ?: return Resultable.fail("Border 2 is not defined.")
        return GUtil.isInBorder(location, border1, border2)
    }

    override fun toString(): String {
        return "WorldDataBuilder(type=$type, regenerator=$regenerator, border1=$border1, border2=$border2, spawn=$spawn, spectator=$spectator)"
    }
}