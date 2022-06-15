package net.hoz.gamecore.api.event.builder.team

import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.lib.world.LocationHolder

data class GameTeamSpawnSetEvent(
    val teamBuilder: GameTeamBuilder,
    val gameBuilder: GameBuilder,
    val location: LocationHolder
) : SEvent
