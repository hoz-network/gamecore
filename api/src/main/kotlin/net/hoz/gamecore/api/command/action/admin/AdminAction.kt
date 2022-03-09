package net.hoz.gamecore.api.command.action.admin

import cloud.commandframework.Command
import cloud.commandframework.CommandManager
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

class AdminAction(
    mainCommandBuilder: Command.Builder<CommandSenderWrapper>,
    commandManager: CommandManager<CommandSenderWrapper>,
    gameManager: GameManager
) : AbstractAction(mainCommandBuilder, commandManager, gameManager) {
    override fun build() {
        TODO("Not yet implemented")
    }

    override fun mainAction0(): Command.Builder<CommandSenderWrapper> {
        TODO("Not yet implemented")
    }
}