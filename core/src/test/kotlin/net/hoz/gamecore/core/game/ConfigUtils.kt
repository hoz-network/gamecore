package net.hoz.gamecore.core.game

import net.hoz.api.data.game.GameConfig

object ConfigUtils {
    fun provideConfig(action: GameConfig.Builder.() -> Unit): GameConfig {
        val config = GameConfig.newBuilder()
        action.invoke(config)
        return config.build()
    }

    object Enabled {
        fun provideEnabled(action: GameConfig.Enabled.Builder.() -> Unit): GameConfig.Enabled {
            val enabled = GameConfig.Enabled.newBuilder()
            action.invoke(enabled)

            return enabled.build()
        }
    }
}