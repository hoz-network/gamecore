package net.hoz.gamecore.core.game.team

import com.iamceph.resulter.core.DataResultable
import net.hoz.api.data.game.ProtoGameTeam
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.api.util.GUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.screamingsandals.lib.world.LocationHolder

class GameTeamImpl(
    private var name: String,
    private var color: NamedTextColor,
    private var spawn: LocationHolder,
    private var target: LocationHolder?,
    private var maxPlayers: Int
) : GameTeam {
    private val players: MutableList<GamePlayer> = mutableListOf()
    private var isAlive = false
    private var frame: GameFrame? = null
    private val unsafe = UnsafeImpl(this)

    override fun color(): NamedTextColor = color

    override fun coloredName(): Component = Component.text(name).color(color)

    override fun spawn(): LocationHolder = spawn

    override fun target(): LocationHolder? = target

    override fun players(): List<GamePlayer> = players

    override fun countPlayers(): Int = players.size

    override fun hasPlayer(player: GamePlayer): Boolean = players.contains(player)

    override fun maxPlayers(): Int = maxPlayers

    override fun alive(): Boolean = isAlive

    override fun frame(): GameFrame? = frame

    override fun toBuilder(): DataResultable<GameTeam.Builder> {
        val builder = GameTeamBuilderImpl(name)
            .color(color)
            .spawn(spawn)
            .maxPlayers(maxPlayers)

        val target = this.target
        if (target != null) {
            builder.target(target)
        }

        return DataResultable.ok(builder)
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