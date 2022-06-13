package net.hoz.gamecore.api.command.action

import cloud.commandframework.Command
import cloud.commandframework.CommandManager
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

abstract class AbstractAction(
    val mainCommand: Command.Builder<CommandSenderWrapper>,
    val commandManager: CommandManager<CommandSenderWrapper>,
    val gameManager: GameManager
) {
    abstract suspend fun build()
}