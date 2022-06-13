package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.DataResultable
import net.hoz.gamecore.api.Buildable
import org.screamingsandals.lib.tasker.TaskerTime

class GameSpawnerSettingsBuilder(
    var spread: Double? = null,
    var max: Int? = null,
    var amount: Int? = null,
    var period: Int? = null,
    var timeUnit: TaskerTime? = null,
) : Buildable.Builder<GameSpawnerSettings> {

    override fun build(): DataResultable<GameSpawnerSettings> {
        val default = default()
        val result = GameSpawnerSettingsImpl(
            spread = spread ?: default.spread,
            max = max ?: default.max,
            amount = amount ?: default.amount,
            period = period ?: default.period,
            timeUnit = timeUnit ?: default.timeUnit
        )

        return DataResultable.ok(result)
    }

    companion object {
        fun default(): GameSpawnerSettings {
            return GameSpawnerSettingsImpl(
                spread = 0.0,
                max = -1,
                amount = 1,
                period = 1,
                timeUnit = TaskerTime.SECONDS
            )
        }
    }

}