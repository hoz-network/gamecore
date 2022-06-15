package net.hoz.gamecore.api.event.builder.world

import net.hoz.api.data.game.ProtoWorldData.BorderType
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.world.WorldDataBuilder
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.lib.world.LocationHolder

data class GameWorldBorderSetEvent(
    val world: WorldDataBuilder,
    val gameBuilder: GameBuilder,
    val location: LocationHolder,
    val borderType: BorderType
) : SEvent
