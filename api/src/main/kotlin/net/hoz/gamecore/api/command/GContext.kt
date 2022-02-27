package net.hoz.gamecore.api.command

import cloud.commandframework.context.CommandContext
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.service.GameManager

val ARENA_FIELD_NAME: CloudKey<String> = SimpleCloudKey.of("gamecore-arena-name", TypeToken.get(String::class.java))

fun <C> CommandContext<C>.gameBuilder(manager: GameManager): GameBuilder? {
    if (this.contains(ARENA_FIELD_NAME)) {
        return manager.builders().find(this[ARENA_FIELD_NAME])
    }
    return null
}

fun <C> CommandContext<C>.gameFrame(manager: GameManager): GameFrame? {
    if (this.contains(ARENA_FIELD_NAME)) {
        return manager.frames().find(this[ARENA_FIELD_NAME])
    }
    return null
}

fun <C> CommandContext<C>.frameName(): String {
    if (this.contains(ARENA_FIELD_NAME)) {
        return this[ARENA_FIELD_NAME]
    }
    return "_UNDEFINED_"
}