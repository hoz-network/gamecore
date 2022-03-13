package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.EnumArgument
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import mu.KotlinLogging
import net.hoz.api.data.game.ProtoWorldData.BorderType
import net.hoz.api.data.game.ProtoWorldData.WorldType
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper

class SetLobbyBorderAction(
    parentAction: AbstractAction,
    private val worldType: WorldType
) : AbstractBuilderSubAction(parentAction) {
    private val log = KotlinLogging.logger {}
    private val BORDER_TYPE: CloudKey<BorderType> =
        SimpleCloudKey.of("game-core-border-type", TypeToken.get(BorderType::class.java))

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("border", ArgumentDescription.of("Sets a border for " + worldType.name))
                .argument(
                    EnumArgument.newBuilder<CommandSenderWrapper?, BorderType?>(
                        BorderType::class.java,
                        BORDER_TYPE.name
                    )
                        .withSuggestionsProvider { _, input -> suggestBorder(input) }
                        .asRequired()
                        .build()
                )
                .builderHandler { sender, gameBuilder, context ->
                    val location = sender.location
                    val borderType = context[BORDER_TYPE]
                    log.debug { "Border[${worldType.name}/${borderType.name}] will be at location: $location" }

                    val world = gameBuilder.world()
                        .lobby {
                            when (borderType) {
                                BorderType.SECOND -> border2 = location
                                BorderType.FIRST -> border1 = location
                                BorderType.UNRECOGNIZED -> {
                                    log.warn { "Cannot set UNRECOGNIZED border type." }
                                }
                            }
                        }

                    log.debug { "Created new ${worldType.name} world builder: $world" }

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_SPAWNER_ADDED)
                        .resolvePrefix()
                        .send(sender)
                }
            )
    }

    private fun suggestBorder(input: String): MutableList<String> {
        val available = mutableListOf(BorderType.FIRST.name, BorderType.SECOND.name)
        val first = available.firstOrNull { it.startsWith(input) }
            ?: return available

        return mutableListOf(first)
    }

}