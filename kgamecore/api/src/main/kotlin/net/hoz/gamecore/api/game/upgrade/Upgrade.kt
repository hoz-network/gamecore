package net.hoz.gamecore.api.game.upgrade

import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.tasker.TaskerTime

interface Upgrade {
    fun team(): GameTeam?
    fun duration(): Double
    fun timeUnit(): TaskerTime
    fun active(): Boolean
    fun expired(): Boolean
}