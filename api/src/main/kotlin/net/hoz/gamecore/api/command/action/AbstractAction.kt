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
    var mainAction: Command.Builder<CommandSenderWrapper>? = null

    fun mainAction(): Command.Builder<CommandSenderWrapper> {
        if (mainAction == null) {
            val built = mainAction0()
            mainAction = built
            return built
        }
        return mainAction as Command.Builder<CommandSenderWrapper>
    }

    abstract fun build()

    protected abstract fun mainAction0(): Command.Builder<CommandSenderWrapper>
}