package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer

class GamePlayerJoinedGameEvent(player: GamePlayer, frame: GameFrame) : GamePlayerEvent(player, frame)