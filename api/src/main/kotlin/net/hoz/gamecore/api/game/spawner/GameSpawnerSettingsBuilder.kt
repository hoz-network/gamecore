package net.hoz.gamecore.api.game.spawner

import org.screamingsandals.lib.tasker.TaskerTime

class GameSpawnerSettingsBuilder(
    var spread: Double? = null,
    var max: Int? = null,
    var amount: Int? = null,
    var period: Int? = null,
    var timeUnit: TaskerTime? = null,
) {

    fun build(): GameSpawnerSettings {
        return GameSpawnerSettingsImpl(
            spread = spread ?: 0.0,
            max = max ?: -1,
            amount = amount ?: 1,
            period = period ?: 1,
            timeUnit = timeUnit ?: TaskerTime.SECONDS
        )
    }
}