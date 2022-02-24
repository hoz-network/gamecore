package net.hoz.gamecore.api.game.spawner

import net.hoz.api.data.game.ProtoSpawnerSettings
import org.screamingsandals.lib.tasker.TaskerTime

internal data class GameSpawnerSettingsImpl(
    val spread: Double,
    val max: Int,
    val amount: Int,
    val period: Int,
    val timeUnit: TaskerTime,
) : GameSpawnerSettings {

    override fun spread(): Double {
        return spread
    }

    override fun max(): Int {
        return max
    }

    override fun amount(): Int {
        return amount
    }

    override fun period(): Int {
        return period
    }

    override fun timeUnit(): TaskerTime {
        return timeUnit
    }

    override fun toBuilder(): GameSpawnerSettingsBuilder {
        return GameSpawnerSettingsBuilder(spread, max, amount, period, timeUnit)
    }

    override fun asProto(): ProtoSpawnerSettings {
        return ProtoSpawnerSettings.newBuilder()
            .setSpread(spread())
            .setMaxSpawnAmount(max())
            .setSpawnAmount(amount())
            .setSpawnPeriod(period())
            .setSpawnTime(timeUnit().asProto())
            .build()
    }
}