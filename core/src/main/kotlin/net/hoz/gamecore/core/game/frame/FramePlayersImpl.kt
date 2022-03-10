package net.hoz.gamecore.core.game.frame

import com.iamceph.resulter.core.Resultable
import mu.KotlinLogging
import net.hoz.gamecore.api.event.player.GamePlayerJoinedGameEvent
import net.hoz.gamecore.api.event.player.GamePlayerPreJoinedGameEvent
import net.hoz.gamecore.api.event.player.spectator.SpectatorJoinedGameEvent
import net.hoz.gamecore.api.event.player.spectator.SpectatorPreJoinGameEvent
import net.hoz.gamecore.api.game.frame.FramePlayers
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import net.hoz.gamecore.core.util.GConfig
import net.kyori.adventure.audience.Audience
import org.screamingsandals.lib.kotlin.fire
import org.screamingsandals.lib.player.gamemode.GameModeHolder
import java.util.*

open class FramePlayersImpl(
    protected val frame: GameFrame
) : FramePlayers {
    private val log = KotlinLogging.logger { }
    private val players: MutableMap<UUID, GamePlayer> = mutableMapOf()

    override fun join(player: GamePlayer): Resultable {
        val playerId = player.uuid
        log.debug("[{}] - Joining to frame[{}].", playerId, frame.uuid())

        if (!player.state.untracked() ||  player.frame != null) {
            log.debug { "[$playerId] - cannot join the player, already joined to frame[${player.frame?.uuid()}]" }
            //TODO: language
            return Resultable.fail("Cannot join the player, already in game.")
        }

        if (frame.manage().isRunning() && !GConfig.ARE_SPECTATORS_ENABLED(frame)) {
            log.debug("[$playerId] - Frame is already running and spectators are disabled, cannot join.")
            return Resultable.fail("This game is running and you cannot join. Sad. :)")
        }

        if (frame.manage().isWaiting()) {
            if (GamePlayerPreJoinedGameEvent(player, frame).fire().cancelled()) {
                log.debug("[{}] - GamePlayerPreJoinedGameEvent cancelled joining.", playerId)
                //TODO: language
                return Resultable.fail("cancelled by event")
            }
        } else {
            if (SpectatorPreJoinGameEvent(player, frame).fire().cancelled()) {
                log.debug("[{}] - SpectatorPreJoinGameEvent cancelled joining.", playerId)
                //TODO: language
                return Resultable.fail("cancelled by event")
            }
        }

        player.unsafe().frame(frame)
        players[playerId] = player

        if (frame.manage().isWaiting()) {
            GamePlayerJoinedGameEvent(player, frame).fire()
            return toLobby(player)
        }

        SpectatorJoinedGameEvent(player, frame).fire()
        return toSpectator(player)
    }

    override fun leave(player: GamePlayer): Resultable {
        val playerFrame = player.frame
        if (player.state.untracked() || playerFrame == null) {
            return Resultable.ok("already left :)")
        }

        if (playerFrame.uuid() != frame.uuid()) {
            return Resultable.fail("I cannot leave you ffs")
        }

        players.remove(player.uuid)
        player.unsafe().frame(null)
        player.unsafe().state(GamePlayer.State.NOT_TRACKED)
        //TODO: hide visuals
        //TODO: move to lobby server

        //TODO: hide visuals
        //TODO: move to lobby server
        return Resultable.ok()
    }

    override fun joinTeam(player: GamePlayer, team: GameTeam): Resultable {
        TODO("Not yet implemented")
    }

    override fun leaveTeam(player: GamePlayer, team: GameTeam): Resultable {
        TODO("Not yet implemented")
    }

    override fun has(uuid: UUID): Boolean {
        return players.containsKey(uuid)
    }

    override fun hasEnough(): Boolean {
        return frame.minPlayers() <= players.size
    }

    override fun find(uuid: UUID): GamePlayer? {
        return players[uuid]
    }

    override fun find(name: String): GamePlayer? {
        return players
            .values
            .firstOrNull { it.name == name }
    }

    override fun all(): Map<UUID, GamePlayer> {
        return players
    }

    override fun toAlive(player: GamePlayer): Resultable {
        if (isUntracedOrWithoutGame(player)) {
            return Resultable.fail("Player is not traced and is not in game.")
        }

        if (GConfig.ARE_TEAMS_ENABLED(frame)) {
            val team = player.team
            if (team == null) {
                //TODO: send message about this mess
                return toSpectator(player)
            }

            val spawn = team.spawn
            player.teleport(spawn) { player.gameMode = GameModeHolder.of("SURVIVAL") }
            player.unsafe().state(GamePlayer.State.ALIVE)
            return Resultable.ok()
        }

        //TODO: work out spawns without teams


        //TODO: work out spawns without teams
        return Resultable.fail("Not implemented yet.")
    }

    override fun toViewer(player: GamePlayer): Resultable {
        if (isUntracedOrWithoutGame(player)) {
            return Resultable.fail("Player is not traced and is not in game.")
        }

        val spectatorSpawn = frame.world()
            .arenaWorld
            .spectator

        if (spectatorSpawn == null) {
            //TODO: log
            return Resultable.fail("Spectator spawn not found.")
        }

        player.teleport(spectatorSpawn) { player.gameMode = GameModeHolder.of("SPECTATOR") }
        player.unsafe().state(GamePlayer.State.VIEWING)

        //TODO: start countdown

        //TODO: start countdown
        return Resultable.ok()
    }

    override fun toSpectator(player: GamePlayer): Resultable {
        if (isUntracedOrWithoutGame(player)) {
            return Resultable.fail("Player is not traced and is not in game.")
        }

        val spectatorSpawn = frame.world()
            .arenaWorld
            .spectator

        if (spectatorSpawn == null) {
            return Resultable.fail("Spectator spawn not found.")
        }

        player.teleport(spectatorSpawn, Runnable { player.setGameMode(GameModeHolder.of("SPECTATOR")) })
        player.unsafe().state(GamePlayer.State.SPECTATOR)

        return Resultable.ok()
    }

    override fun toLobby(player: GamePlayer): Resultable {
        if (isUntracedOrWithoutGame(player)) {
            return Resultable.fail("Player is not traced and is not in game.")
        }

        val lobby = frame.world()
            .lobbyWorld
            .spawn

        player.teleport(lobby) { player.gameMode = GameModeHolder.of("ADVENTURE") }
        player.unsafe().state(GamePlayer.State.LOBBY)

        return Resultable.ok()
    }

    override fun audiences(): MutableIterable<Audience> {
        return players.values
    }

    private fun isUntracedOrWithoutGame(player: GamePlayer): Boolean {
        return player.state.untracked() || player.frame == null
    }
}