package net.hoz.gamecore.api.game.frame.builder

import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder

/**
 * Manages teams in [GameBuilder]
 */
interface BuilderTeams : BuilderBase<GameTeamBuilder, GameTeam, String> {

}