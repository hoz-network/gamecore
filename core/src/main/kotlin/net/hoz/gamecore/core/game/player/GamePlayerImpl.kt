package net.hoz.gamecore.core.game.player

import net.hoz.api.data.NetPlayer
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.player.ExtendablePlayerWrapper
import org.screamingsandals.lib.player.PlayerWrapper

class GamePlayerImpl(
    playerWrapper: PlayerWrapper,
    private var data: NetPlayer
) : ExtendablePlayerWrapper(playerWrapper), GamePlayer {
    private var team: GameTeam? = null
    private var frame: GameFrame? = null
    private var state = GamePlayer.State.NOT_TRACED
    private val unsafe = UnsafeImpl(this)

    override fun data(): NetPlayer {
       return data
    }

    override fun state(): GamePlayer.State {
        return state
    }

    override fun team(): GameTeam? {
        return team
    }

    override fun frame(): GameFrame? {
        return frame
    }

    override fun unsafe(): GamePlayer.Unsafe {
        return unsafe
    }

    private class UnsafeImpl(private val player: GamePlayerImpl) : GamePlayer.Unsafe {
        override fun data(data: NetPlayer) {
            player.data = data
        }

        override fun state(state: GamePlayer.State) {
           player.state = state
        }

        override fun team(team: GameTeam?) {
            player.team = team
        }

        override fun frame(frame: GameFrame?) {
            player.frame = frame
        }

    }
}