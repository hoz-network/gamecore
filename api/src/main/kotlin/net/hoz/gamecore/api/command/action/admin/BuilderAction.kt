package net.hoz.gamecore.api.command.action.admin

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import cloud.commandframework.CommandManager
import net.hoz.gamecore.api.command.COMMAND_FRAME_FIELD
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.game.GameBuilderArgument
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

class BuilderAction(
    mainCommandBuilder: Command.Builder<CommandSenderWrapper>,
    commandManager: CommandManager<CommandSenderWrapper>,
    gameManager: GameManager
) : AbstractAction(mainCommandBuilder, commandManager, gameManager) {
    override fun build() {
        TODO("Not yet implemented")
    }

    override fun mainAction0(): Command.Builder<CommandSenderWrapper> {
        //TODO: lang
        return mainCommand.literal("builder", ArgumentDescription.of("Entrypoint for creating Frames."))
            .argument(GameBuilderArgument.of(COMMAND_FRAME_FIELD, gameManager))
    }
}