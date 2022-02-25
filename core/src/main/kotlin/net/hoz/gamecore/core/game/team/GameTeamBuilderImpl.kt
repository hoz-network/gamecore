package net.hoz.gamecore.core.game.team

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder

class GameTeamBuilderImpl(name: String) : GameTeamBuilder(name, null, null, null, null) {

    override fun build(): DataResultable<GameTeam> {
        val color = this.color ?: return DataResultable.fail("Color is not defined.")
        val spawn = this.spawn ?: return DataResultable.fail("Spawn is not defined.")
        val maxPlayers = this.maxPlayers ?: return DataResultable.fail("Max players are not defined.")

        return DataResultable.ok(GameTeamImpl(name, color, spawn, target, maxPlayers))
    }
}