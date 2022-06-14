package net.hoz.gamecore.core.game.team

import net.hoz.api.data.game.ProtoGameTeam
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.util.GUtil
import net.hoz.gamecore.api.util.toProto
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.spectator.Component
import org.screamingsandals.lib.world.LocationHolder

data class GameTeamImpl(
    private var name: String,
    override var color: Color,
    override var spawn: LocationHolder,
    override var target: LocationHolder?,
    override var maxPlayers: Int
) : GameTeam {
    override var frame: GameFrame? = null

    override val players: MutableList<GamePlayer> = mutableListOf()
    private var isAlive = false
    private val unsafe = UnsafeImpl(this)

    override fun coloredName(): Component = Component.text(name).withColor(color)

    override fun countPlayers(): Int = players.size

    override fun hasPlayer(player: GamePlayer): Boolean = players.contains(player)

    override fun alive(): Boolean = isAlive

    override fun toBuilder(builder: GameTeamBuilder.() -> Unit): GameTeamBuilder {
        val data = GameTeamBuilderImpl(
            name,
            color,
            spawn,
            target,
            maxPlayers,
        )

        builder.invoke(data)

        return data
    }

    override fun unsafe(): GameTeam.Unsafe = unsafe

    override fun name(): String = name

    override fun asProto(): ProtoGameTeam {
        val builder = ProtoGameTeam.newBuilder()
            .setName(name)
            .setColor(color.toProto())
            .setMaxPlayers(maxPlayers)
            .setSpawn(spawn.asProto())

        val target = this.target
        if (target != null) {
            builder.target = target.asProto()
        }

        return builder.build()
    }

    class UnsafeImpl(private val team: GameTeamImpl) : GameTeam.Unsafe {
        override fun addPlayer(player: GamePlayer) {
            team.players.add(player)
        }

        override fun removePlayer(player: GamePlayer) {
            team.players.remove(player)
        }

        override fun alive(alive: Boolean) {
            team.isAlive = alive
        }

        override fun frame(frame: GameFrame?) {
            team.frame = frame
        }
    }
}