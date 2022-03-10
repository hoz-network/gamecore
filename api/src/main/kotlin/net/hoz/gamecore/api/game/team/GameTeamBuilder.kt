package net.hoz.gamecore.api.game.team

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.Buildable
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.utils.Nameable
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
) : Nameable, Buildable.Builder<GameTeam> {
    override fun name(): String = name

    /**
     * Creates new [GameTeam] from this builder.
     *
     * NOTE: the team NEEDS to have all properties set, otherwise it is not possible to save the team.
     * NOTE: this method WILL be called by the GameBuilder, you don't need to do it.
     *
     * @return [DataResultable] result of this operation.
     */
    abstract override fun build(): DataResultable<GameTeam>
}
