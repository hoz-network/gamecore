package net.hoz.gamecore.api.command.action.admin

import cloud.commandframework.Command
import cloud.commandframework.CommandManager
import net.hoz.gamecore.api.command.GContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.argument.game.GameFrameArgument
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

class AdminAction(
    mainCommandBuilder: Command.Builder<CommandSenderWrapper>,
    commandManager: CommandManager<CommandSenderWrapper>,
    gameManager: GameManager
) : AbstractAction(mainCommandBuilder, commandManager, gameManager) {

    override suspend fun build() {
        val mainAction = buildMainAction()
        TODO("Not yet implemented")
    }

    fun buildMainAction(): Command.Builder<CommandSenderWrapper> {
        //TODO: lang
        return mainCommand.literal("admin", { "Entrypoint for managing games." })
            .literal("arena")
            .argument(GameFrameArgument.of(GContext.COMMAND_FRAME_FIELD.name, gameManager))
    }
}