package net.hoz.gamecore.api.event.player

import org.screamingsandals.lib.event.SEvent
import org.screamingsandals.lib.player.PlayerWrapper

data class GamePlayerUnregisteredEvent(val player: PlayerWrapper) : SEvent