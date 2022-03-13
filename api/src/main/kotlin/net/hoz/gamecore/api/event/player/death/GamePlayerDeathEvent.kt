package net.hoz.gamecore.api.event.player.death

import net.hoz.gamecore.api.event.player.GamePlayerEvent
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.player.GamePlayer
import net.kyori.adventure.text.Component
import org.screamingsandals.lib.item.Item
import org.screamingsandals.lib.utils.ObjectLink

data class GamePlayerDeathEvent(
    override val player: GamePlayer,
    override val frame: GameFrame,
    val deathMessage: ObjectLink<Component>,
    val drops: MutableCollection<Item>,
    val keepInventory: ObjectLink<Boolean>,
    val shouldDropExperience: ObjectLink<Boolean>,
    val keepLevel: ObjectLink<Boolean>,
    val newLevel: ObjectLink<Int>,
    val newTotalExp: ObjectLink<Int>,
    val newExp: ObjectLink<Int>,
    val droppedExp: ObjectLink<Int>,
    val killer: GamePlayer?
) : GamePlayerEvent(player, frame)