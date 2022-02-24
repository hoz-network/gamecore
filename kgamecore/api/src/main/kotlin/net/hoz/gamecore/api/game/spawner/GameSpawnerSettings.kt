package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.pack.ProtoWrapper
import net.hoz.api.data.game.ProtoSpawnerSettings
import org.screamingsandals.lib.tasker.TaskerTime

/**
 * Spawner settings.
 */
interface GameSpawnerSettings : ProtoWrapper<ProtoSpawnerSettings?> {
    /**
     * How big spread of the spawned resources should be.
     *
     * @return max spread.
     */
    fun spread(): Double

    /**
     * How many resources are we allowed to spawn.
     *
     * @return max resources.
     */
    fun max(): Int

    /**
     * How many resources should we spawn at once.
     *
     * @return max amount per one spawn.
     */
    fun amount(): Int

    /**
     * Period of spawning
     *
     * @return spawning period
     */
    fun period(): Int

    /**
     * In what time units the [GameSpawnerSettings.period] is.
     *
     * @return time unit.
     */
    fun timeUnit(): TaskerTime

    /**
     * Creates a builder with values that are set in these settings.
     *
     * @return builder
     */
    fun toBuilder(): Builder

    /**
     * Builder for spawner settings.
     */
    interface Builder {
        fun spread(spread: Double): Builder
        fun max(maxSpawned: Int): Builder
        fun amount(amount: Int): Builder
        fun period(period: Int): Builder
        fun timeUnit(time: TaskerTime?): Builder

        /**
         * Builds the [GameSpawnerSettings]
         *
         * @return
         */
        fun build(): GameSpawnerSettings
    }

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return new builder.
         */
        fun builder(): Builder {
            return GameSpawnerSettingsBuilder()
        }

        /**
         * Creates new builder from .proto settings.
         *
         * @param settings settings to create from.
         * @return new builder.
         */
        fun builder(settings: ProtoSpawnerSettings): Builder {
            return GameSpawnerSettingsBuilder(
                settings.spread,
                settings.maxSpawnAmount,
                settings.spawnAmount,
                settings.spawnPeriod,
                TaskerTime.from(settings.spawnTime)
            )
        }
    }
}