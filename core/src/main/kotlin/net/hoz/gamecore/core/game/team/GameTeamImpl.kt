package net.hoz.gamecore.core.game.team

import net.hoz.api.data.game.ProtoGameTeam
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import net.hoz.gamecore.api.util.GUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.world.LocationHolder

data class GameTeamImpl(
    private var name: String,
    override var color: NamedTextColor,
    override var spawn: LocationHolder,
    override var target: LocationHolder?,
    override var maxPlayers: Int
) : GameTeam {
    override val players: MutableList<GamePlayer> = mutableListOf()
    private var isAlive = false
    private var frame: GameFrame? = null
    private val unsafe = UnsafeImpl(this)

    override fun coloredName(): Component = Component.text(name).color(color)

    override fun countPlayers(): Int = players.size

    override fun hasPlayer(player: GamePlayer): Boolean = players.contains(player)

    override fun alive(): Boolean = isAlive

    override fun frame(): GameFrame? = frame

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
            .setColor(GUtil.toProtoColor(color))
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