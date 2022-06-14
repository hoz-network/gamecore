package net.hoz.gamecore.api.util

import mu.KotlinLogging
import net.hoz.api.data.MinecraftColor
import net.hoz.gamecore.api.lang.CommandLang
import net.hoz.gamecore.api.lang.CommonLang
import net.hoz.netapi.client.lang.LangResultable
import org.screamingsandals.lib.spectator.Color
import org.screamingsandals.lib.world.LocationHolder
import java.util.*

fun MinecraftColor.fromProto(): Color {
    return Color.named(this.name)
        ?: throw UnsupportedOperationException("Cannot convert color,")
}

fun Color.toProto(): MinecraftColor = MinecraftColor.valueOf(this.toString())

object GUtil {
    private val log = KotlinLogging.logger {}

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

    /**
     * Performs a check if given location is inside border of given points.
     *
     * @param location location to check onto
     * @param border1  border1 location
     * @param border2  border2 location
     * @return result of the operation.
     */
    fun isInBorder(location: LocationHolder, border1: LocationHolder, border2: LocationHolder): LangResultable {
        val locationWorldId = location.world.uuid
        val border1Id = border1.world.uuid
        val border2Id = border2.world.uuid

        if (border1Id != locationWorldId) {
            return LangResultable.fail(CommandLang.ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
        }
        if (border2Id != locationWorldId) {
            return LangResultable.fail(CommandLang.ERROR_BUILDER_BORDER_WORLD_IS_DIFFERENT)
        }

        // this is some black magic copied from Misat
        val min = LocationHolder(
            border1.x.coerceAtMost(border2.x),
            border1.y.coerceAtMost(border2.y),
            border1.z.coerceAtMost(border2.z)
        )
        val max = LocationHolder(
            border1.x.coerceAtLeast(border2.x),
            border1.y.coerceAtLeast(border2.y),
            border1.z.coerceAtLeast(border2.z)
        )

        return if (min.x <= location.x
            && min.y <= location.y
            && min.z <= location.z
            && max.x >= location.x
            && max.y >= location.y
            && max.z >= location.z
        ) {
            LangResultable.ok()
        } else LangResultable.fail(CommonLang.LOCATION_OUTSIDE_BORDER)
    }
}