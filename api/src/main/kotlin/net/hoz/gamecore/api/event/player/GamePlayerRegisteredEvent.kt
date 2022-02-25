package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.game.player.GamePlayer
import org.screamingsandals.lib.event.SEvent

data class GamePlayerRegisteredEvent(val player: GamePlayer) : SEvent