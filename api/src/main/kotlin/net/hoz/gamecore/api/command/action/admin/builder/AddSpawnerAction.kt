package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.BooleanArgument
import mu.KotlinLogging
import net.hoz.gamecore.api.command.GContext.COMMAND_SPAWNER_TYPE_FIELD
import net.hoz.gamecore.api.command.GContext.COMMAND_SPAWNER_USE_GLOBAL_CONFIG
import net.hoz.gamecore.api.command.GContext.COMMAND_SPAWNER_USE_HOLOGRAMS
import net.hoz.gamecore.api.command.GContext.COMMAND_TEAM_BUILDER_FIELD
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.spawner.GameSpawnerTypeArgument
import net.hoz.gamecore.api.command.argument.team.GameTeamArgument
import net.hoz.gamecore.api.command.builderHandler
import net.hoz.gamecore.api.command.getOrNull
import net.hoz.gamecore.api.lang.CommandLang
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper
import java.util.*

private val log = KotlinLogging.logger {}

class AddSpawnerAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {
    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("spawner", ArgumentDescription.of("Adds new GameSpawner."))
                .argument(GameSpawnerTypeArgument.of(COMMAND_SPAWNER_TYPE_FIELD, gameManager))
                .argument(GameTeamArgument.optional(COMMAND_TEAM_BUILDER_FIELD, gameManager))
                .argument(BooleanArgument.optional(COMMAND_SPAWNER_USE_HOLOGRAMS.name, true))
                .argument(BooleanArgument.optional(COMMAND_SPAWNER_USE_GLOBAL_CONFIG.name, true))
                .builderHandler { sender, gameBuilder, context ->
                    val location = sender.location
                    log.debug { "Spawner location will be: $location" }

                    val spawnerType = context[COMMAND_SPAWNER_TYPE_FIELD]
                    val useHolograms = context[COMMAND_SPAWNER_USE_HOLOGRAMS]
                    val useGlobalConfig = context[COMMAND_SPAWNER_USE_GLOBAL_CONFIG]
                    val team = context.getOrNull(COMMAND_TEAM_BUILDER_FIELD)

                    log.debug { "Trying to create new spawner builder.." }

                    val spawner = gameBuilder.spawners
                        .builder(UUID.randomUUID()) {
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
}
