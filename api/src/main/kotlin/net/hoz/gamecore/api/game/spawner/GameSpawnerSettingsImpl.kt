package net.hoz.gamecore.api.game.spawner

import net.hoz.api.data.game.ProtoSpawnerSettings
import org.screamingsandals.lib.tasker.TaskerTime

internal data class GameSpawnerSettingsImpl(
    override val spread: Double,
    override val max: Int,
    override val amount: Int,
    override val period: Int,
    override val timeUnit: TaskerTime,
) : GameSpawnerSettings {

    override fun toBuilder(): GameSpawnerSettingsBuilder {
        return GameSpawnerSettingsBuilder(spread, max, amount, period, timeUnit)
    }

    override fun asProto(): ProtoSpawnerSettings {
        return ProtoSpawnerSettings.newBuilder()
            .setSpread(spread)
            .setMaxSpawnAmount(max)
            .setSpawnAmount(amount)
            .setSpawnPeriod(period)
            .setSpawnTime(timeUnit.asProto())
            .build()
    }
}