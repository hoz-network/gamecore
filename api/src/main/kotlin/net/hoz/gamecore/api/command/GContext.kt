package net.hoz.gamecore.api.command

import cloud.commandframework.context.CommandContext
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.service.GameManager

val COMMAND_CONFIG_FIELD: CloudKey<String> = SimpleCloudKey.of("gamecore-config-name", TypeToken.get(String::class.java))
val COMMAND_ARENA_FIELD: CloudKey<String> = SimpleCloudKey.of("gamecore-arena-name", TypeToken.get(String::class.java))

fun <C> CommandContext<C>.findGameBuilder(manager: GameManager): GameBuilder? {
    if (this.contains(COMMAND_ARENA_FIELD)) {
        return manager.builders().find(this[COMMAND_ARENA_FIELD])
    }
    return null
}

fun <C> CommandContext<C>.findGameFrame(manager: GameManager): GameFrame? {
    if (this.contains(COMMAND_ARENA_FIELD)) {
        return manager.frames().find(this[COMMAND_ARENA_FIELD])
    }
    return null
}

fun <C> CommandContext<C>.findArenaName(): String {
    if (this.contains(COMMAND_ARENA_FIELD)) {
        return this[COMMAND_ARENA_FIELD]
    }
    return "_UNDEFINED_"
}

fun <C> CommandContext<C>.findOrUnknown(input: CloudKey<String>): String {
    if (this.contains(input)) {
        return this[input]
    }
    return "_UNDEFINED_"
}