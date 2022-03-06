package net.hoz.gamecore.api.util

import mu.KotlinLogging
import net.hoz.api.data.MinecraftColor
import net.kyori.adventure.text.format.NamedTextColor

interface GUtil {

    companion object {
        private val log = KotlinLogging.logger { }

        fun findMatchingOrAvailable(input: String, available: List<String>): MutableList<String> =
            findMatchingOrAvailable(input, available, "none found")

        fun findMatchingOrAvailable(
            input: String,
            available: List<String>,
            ifEmpty: String
        ): MutableList<String> {
            if (available.isEmpty()) return mutableListOf(ifEmpty)
            return available.filter { input.startsWith(it) }
                .takeWhile { input.startsWith(it) }
                .ifEmpty { available }
                .toMutableList()
        }

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