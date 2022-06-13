package net.hoz.gamecore.api.command

import cloud.commandframework.Command
import cloud.commandframework.context.CommandContext
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import cloud.commandframework.kotlin.coroutines.extension.suspendingHandler
import io.leangen.geantyref.TypeToken
import net.hoz.api.data.GameType
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.api.data.game.StoreHolder
import net.hoz.gamecore.api.command.GContext.COMMAND_GAME_BUILDER_FIELD
import net.hoz.gamecore.api.game.frame.GameFrame
import net.hoz.gamecore.api.game.frame.builder.GameBuilder
import net.hoz.gamecore.api.game.spawner.GameSpawnerType
import net.hoz.gamecore.api.game.team.GameTeamBuilder
import org.screamingsandals.lib.kotlin.unwrap
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper
import org.screamingsandals.lib.spectator.Color

fun <C : CommandSenderWrapper> CommandContext<C>.player(): PlayerWrapper {
    return this.sender.unwrap(PlayerWrapper::class)
}

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
        val builder = it.getOrNull(COMMAND_GAME_BUILDER_FIELD)
            ?: return@handler

        handler.invoke(sender, builder, it)
    }
    return this
}

fun <C : CommandSenderWrapper> Command.Builder<C>.suspendingBuilderHandler(
    handler: suspend (PlayerWrapper, GameBuilder, CommandContext<C>) -> Unit
): Command.Builder<C> {
    this.suspendingHandler {
        val sender = it.sender.unwrap(PlayerWrapper::class)
        val builder = it.getOrNull(COMMAND_GAME_BUILDER_FIELD)
            ?: return@suspendingHandler

        handler.invoke(sender, builder, it)
    }
    return this
}


object GContext {
    val COMMAND_GAME_TYPE: CloudKey<GameType> =
        SimpleCloudKey.of("gamecore-game-type", TypeToken.get(GameType::class.java))

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

    val COMMAND_TEAM_NAME: CloudKey<String> =
        SimpleCloudKey.of("game-core-team-name", TypeToken.get(String::class.java))

    val COMMAND_TEAM_COLOR: CloudKey<Color> =
        SimpleCloudKey.of("game-core-team-color", TypeToken.get(Color::class.java))

    val COMMAND_TEAM_MAX_PLAYERS: CloudKey<Int> =
        SimpleCloudKey.of("game-core-team-max-players", TypeToken.get(Int::class.java))

    val COMMAND_SPAWNER_USE_HOLOGRAMS: CloudKey<Boolean> =
        SimpleCloudKey.of("game-core-spawner-use-holograms", TypeToken.get(Boolean::class.java))

    val COMMAND_SPAWNER_USE_GLOBAL_CONFIG: CloudKey<Boolean> =
        SimpleCloudKey.of("game-core-spawner-use-global-config", TypeToken.get(Boolean::class.java))

    val COMMAND_STORE_NAME: CloudKey<String> =
        SimpleCloudKey.of("game-core-store-name", TypeToken.get(String::class.java))

    val COMMAND_STORE_HOLDER: CloudKey<StoreHolder> =
        SimpleCloudKey.of("game-core-store-holder", TypeToken.get(StoreHolder::class.java))

    val COMMAND_BORDER_TYPE: CloudKey<ProtoWorldData.BorderType> =
        SimpleCloudKey.of("game-core-border-type", TypeToken.get(ProtoWorldData.BorderType::class.java))
}