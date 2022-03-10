package net.hoz.gamecore.api.command

import cloud.commandframework.Command
import cloud.commandframework.context.CommandContext
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper

val COMMAND_CONFIG_FIELD: CloudKey<String> =
    SimpleCloudKey.of("gamecore-config", TypeToken.get(String::class.java))

val COMMAND_FRAME_FIELD: CloudKey<GameFrame> =
    SimpleCloudKey.of("gamecore-frame", TypeToken.get(GameFrame::class.java))

val COMMAND_ARENA_NAME_FIELD: CloudKey<String> =
    SimpleCloudKey.of("gamecore-arena-name", TypeToken.get(String::class.java))

val COMMAND_GAME_BUILDER_FIELD: CloudKey<GameBuilder> =
    SimpleCloudKey.of("gamecore-frame-builder", TypeToken.get(GameBuilder::class.java))

val COMMAND_SPAWNER_TYPE_FIELD: CloudKey<GameSpawnerType> =
    SimpleCloudKey.of("gamecore-spawner-type", TypeToken.get(GameSpawnerType::class.java))

val COMMAND_TEAM_BUILDER_FIELD: CloudKey<GameTeamBuilder> =
    SimpleCloudKey.of("gamecore-team-builder", TypeToken.get(GameTeamBuilder::class.java))

val COMMAND_STORE_HOLDER: CloudKey<StoreHolder> =
    SimpleCloudKey.of("game-core-store-holder", TypeToken.get(StoreHolder::class.java))

fun <C, K : Any> CommandContext<C>.getOrNull(input: CloudKey<K>): K? {
    if (this.contains(input)) {
        return this[input]
    }
    return null
}

fun <C : CommandSenderWrapper> Command.Builder<C>.builderHandler(
    handler: (PlayerWrapper, GameBuilder, CommandContext<C>) -> Unit
): Command.Builder<C> {
    this.handler {
        val sender = it.sender.unwrap(PlayerWrapper::class)
        val builder = it.getOrNull(COMMAND_GAME_BUILDER_FIELD) ?: return@handler

        handler.invoke(sender, builder, it)
    }
    return this
}