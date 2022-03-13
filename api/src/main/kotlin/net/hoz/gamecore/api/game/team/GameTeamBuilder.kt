package net.hoz.gamecore.api.game.team

import net.hoz.gamecore.api.Buildable
import net.kyori.adventure.text.format.NamedTextColor
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
    var color: NamedTextColor?,
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
