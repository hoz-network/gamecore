package net.hoz.gamecore.api.event.player.spectator

import net.hoz.gamecore.api.event.player.GamePlayerEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer

class SpectatorJoinedGameEvent(player: GamePlayer, frame: GameFrame) : GamePlayerEvent(player, frame)