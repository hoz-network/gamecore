package net.hoz.gamecore.api.game.spawner

import com.iamceph.resulter.core.DataResultable
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
    fun toBuilder(): GameSpawnerSettingsBuilder

    companion object {
        /**
         * Creates new empty builder.
         *
         * @return new builder.
         */
        fun builder(builder: GameSpawnerSettingsBuilder.() -> Unit): DataResultable<GameSpawnerSettings> {
            val data = GameSpawnerSettingsBuilder()
            builder.invoke(data)
            return data.build()
        }

        /**
         * Creates new builder from .proto settings.
         *
         * @param settings settings to create from.
         * @return new builder.
         */
        fun of(settings: ProtoSpawnerSettings): DataResultable<GameSpawnerSettings> =
            GameSpawnerSettingsBuilder(
                settings.spread,
                settings.maxSpawnAmount,
                settings.spawnAmount,
                settings.spawnPeriod,
                TaskerTime.from(settings.spawnTime)
            ).build()
    }
}