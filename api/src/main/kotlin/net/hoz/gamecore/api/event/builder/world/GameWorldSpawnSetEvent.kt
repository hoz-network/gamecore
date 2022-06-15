package net.hoz.gamecore.api.event.builder.world

import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.world.WorldDataBuilder
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.lib.world.LocationHolder

data class GameWorldSpawnSetEvent(
    val world: WorldDataBuilder,
    val gameBuilder: GameBuilder,
    val location: LocationHolder
) : SEvent
