package net.hoz.gamecore.api.game.spawner

import net.hoz.gamecore.api.game.spawner.GameSpawnerSettings
import net.hoz.gamecore.api.game.spawner.GameSpawnerSettingsImpl
import org.screamingsandals.lib.tasker.TaskerTime

internal data class GameSpawnerSettingsBuilder(
    var spread: Double = 0.0,
    var max: Int = 0,
    var amount: Int = 0,
    var period: Int = 0,
    var timeUnit: TaskerTime? = null,
) : GameSpawnerSettings.Builder {
    override fun spread(spread: Double): GameSpawnerSettings.Builder {
        this.spread = spread
        return this
    }

    override fun max(maxSpawned: Int): GameSpawnerSettings.Builder {
        this.max = maxSpawned
        return this
    }

    override fun amount(amount: Int): GameSpawnerSettings.Builder {
        this.amount = amount
        return this
    }

    override fun period(period: Int): GameSpawnerSettings.Builder {
        this.period = period
        return this
    }

    override fun timeUnit(time: TaskerTime?): GameSpawnerSettings.Builder {
        this.timeUnit = time
        return this
    }

    override fun build(): GameSpawnerSettings {
        return GameSpawnerSettingsImpl(spread, max, amount, period, timeUnit ?: TaskerTime.SECONDS)
    }
}