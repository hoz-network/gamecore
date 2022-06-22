package net.hoz.gamecore.api.game.frame.builder

import net.hoz.gamecore.api.game.frame.builder.base.BuilderBase
import net.hoz.gamecore.api.game.spawner.GameSpawner
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder

/**
 * Builder for the [GameTeam].
 */
interface BuilderTeams : BuilderBase<GameTeamBuilder, GameTeam, String>