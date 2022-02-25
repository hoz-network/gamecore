package net.hoz.gamecore.api.util

import mu.KotlinLogging
import net.hoz.api.data.MinecraftColor
import net.kyori.adventure.text.format.NamedTextColor

interface GUtil {

    companion object {
        private val log = KotlinLogging.logger { }

        fun fromProtoColor(minecraftColor: MinecraftColor): NamedTextColor {
            val textColor = NamedTextColor.NAMES.value(minecraftColor.name)
            if (textColor == null) {
                log.warn { "Cannot convert color $minecraftColor to NamedTextColor! Using LIGHT_PURPLE." }
                return NamedTextColor.LIGHT_PURPLE
            }

            return textColor
        }

        fun toProtoColor(color: NamedTextColor): MinecraftColor = MinecraftColor.valueOf(color.toString())
    }
}