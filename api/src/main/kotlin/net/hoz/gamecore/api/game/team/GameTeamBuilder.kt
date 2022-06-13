package net.hoz.gamecore.api.game.team

import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.world.LocationHolder

/**
 * Builder of the team
 */
abstract class GameTeamBuilder(
    /**
     * Name of the team
     */
    val name: String,
    /**
     * Color of the team
     */
    var color: Color?,
    /**
     * Spawn location of the team
     */
    var spawn: LocationHolder?,
    /**
     * Target of the team - the point that other teams need to destroy
     */
    var target: LocationHolder?,
    /**
     * Max players available
     */
    var maxPlayers: Int?
) : Buildable.Builder<GameTeam>
