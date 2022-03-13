package net.hoz.gamecore.api.game.world

import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.world.LocationHolder

abstract class WorldDataBuilder(
    val type: ProtoWorldData.WorldType,
    val regenerator: WorldRegenerator,
    var border1: LocationHolder? = null,
    var border2: LocationHolder? = null,
    var spawn: LocationHolder? = null,
    var spectator: LocationHolder? = null,
): Buildable.Builder<WorldData>