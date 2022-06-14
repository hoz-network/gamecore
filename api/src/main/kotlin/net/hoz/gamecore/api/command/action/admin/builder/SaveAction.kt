package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.kotlin.coroutines.extension.suspendingHandler
import mu.KotlinLogging
import net.hoz.gamecore.api.command.GContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.getOrNull
import net.hoz.gamecore.api.command.player
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.player.PlayerWrapper
import org.screamingsandals.lib.sender.CommandSenderWrapper
import org.screamingsandals.lib.spectator.Component

private val log = KotlinLogging.logger {}

class SaveAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager.command(
            builder.literal("save", ArgumentDescription.of("Saves given frame."))
                .senderType(PlayerWrapper::class.java)
                .suspendingHandler(handler = {
                    val sender = it.player()
                    val gameBuilder = it.getOrNull(GContext.COMMAND_GAME_BUILDER_FIELD)
                        ?: return@suspendingHandler

                    val isReady = gameBuilder.manage.checkIntegrity()
                    if (isReady.isFail) {
                        log.debug { "Cannot save GameBuilder because ${isReady.messages()}" }

                        Message.of(CommandLang.ERROR_BUILDER_GAME_CANNOT_BE_SAVED)
                            .placeholder("game-name", gameBuilder.name())
                            .resolvePrefix()
                            .send(sender)
                        isReady.messages()
                            .forEach { message ->
                                //TODO
                            }
                    }

                    val saved = gameBuilder.manage.save()
                    if (saved.isFail) {
                        //TODO: lang
                        Message.of(CommandLang.ERROR_BUILDER_GAME_CANNOT_BE_SAVED)
                            .placeholder("game-name", gameBuilder.name())
                            .resolvePrefix()
                            .send(sender)
                        sender.sendMessage(Component.text(saved.message()))
                        return@suspendingHandler
                    }

                    Message.of(CommandLang.SUCCESS_BUILDER_GAME_SAVED)
                        .placeholder("game-name", gameBuilder.name())
                        .resolvePrefix()
                        .send(sender)
                })
        )
    }
}