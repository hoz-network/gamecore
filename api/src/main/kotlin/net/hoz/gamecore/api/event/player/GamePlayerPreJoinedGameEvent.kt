package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer

class GamePlayerPreJoinedGameEvent(
    player: GamePlayer,
    frame: GameFrame
) : GamePlayerCancellableEvent(player, frame)