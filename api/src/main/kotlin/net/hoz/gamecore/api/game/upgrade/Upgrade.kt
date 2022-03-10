package net.hoz.gamecore.api.game.upgrade

import net.hoz.gamecore.api.game.team.GameTeam
import org.screamingsandals.lib.tasker.TaskerTime

interface Upgrade {
    val team: GameTeam?
    val duration: Double
    val timeUnit: TaskerTime
    val active: Boolean
    val expired: Boolean
}