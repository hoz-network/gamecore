package net.hoz.gamecore.api.event.player.spectator

import net.hoz.gamecore.api.event.player.GamePlayerCancellableEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer

class SpectatorPreJoinGameEvent(player: GamePlayer, frame: GameFrame) : GamePlayerCancellableEvent(player, frame)