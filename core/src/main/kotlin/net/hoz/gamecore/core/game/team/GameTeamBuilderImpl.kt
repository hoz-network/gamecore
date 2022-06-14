package net.hoz.gamecore.core.game.team

import com.iamceph.resulter.core.DataResultable
import com.iamceph.resulter.kotlin.dataResultable
import net.hoz.api.data.game.ProtoGameTeam
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.util.GUtil
import net.hoz.gamecore.api.util.fromProto
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.world.LocationHolder
import org.screamingsandals.lib.world.LocationMapper

class GameTeamBuilderImpl(
    name: String,
    color: Color? = null,
    spawn: LocationHolder? = null,
    target: LocationHolder? = null,
    maxPlayers: Int? = null
) : GameTeamBuilder(name, color, spawn, target, maxPlayers) {

    companion object {
        fun fromProto(data: ProtoGameTeam): GameTeamBuilder = GameTeamBuilderImpl(
            data.name,
            data.color.fromProto(),
            LocationMapper.resolve(data.spawn).orElse(null),
            LocationMapper.resolve(data.target).orElse(null),
            data.maxPlayers
        )
    }

    override fun build(): DataResultable<GameTeam> {
        val color = this.color ?: return DataResultable.fail("Color is not defined.")
        val spawn = this.spawn ?: return DataResultable.fail("Spawn is not defined.")
        val maxPlayers = this.maxPlayers ?: return DataResultable.fail("Max players are not defined.")

        return dataResultable { GameTeamImpl(name, color, spawn, target, maxPlayers) }
    }
}