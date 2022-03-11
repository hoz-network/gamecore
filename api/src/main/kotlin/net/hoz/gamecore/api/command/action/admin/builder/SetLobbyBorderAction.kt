package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.BooleanArgument
import cloud.commandframework.arguments.standard.EnumArgument
import cloud.commandframework.keys.CloudKey
import cloud.commandframework.keys.SimpleCloudKey
import io.leangen.geantyref.TypeToken
import mu.KotlinLogging
import net.hoz.api.data.game.ProtoWorldData.BorderType
import net.hoz.api.data.game.ProtoWorldData.WorldType
import net.hoz.gamecore.api.command.COMMAND_SPAWNER_TYPE_FIELD
import net.hoz.gamecore.api.command.COMMAND_TEAM_BUILDER_FIELD
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.spawner.GameSpawnerTypeArgument
import net.hoz.gamecore.api.command.argument.team.GameTeamArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.command.getOrNull
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper
import java.util.*

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

                    val world = gameBuilder.world().arenaWorld
                    if (world == null) {

                    }
                    val spawner = gameBuilder.spawners()
                        .add(UUID.randomUUID()) {
                            this.location = location
                            this.useHolograms = useHolograms
                            this.useGlobalValues = useGlobalConfig
                            this.team = team?.name
                            types.add(spawnerType)
                        }

                    log.debug { "Created new spawner builder: $spawner" }

                    //TODO: lang
                    Message.of(CommandLang.SUCCESS_BUILDER_SPAWNER_ADDED)
                        .placeholder("spawner-name", spawner.id.toString())
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