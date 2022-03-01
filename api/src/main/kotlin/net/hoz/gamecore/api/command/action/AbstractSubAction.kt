package net.hoz.gamecore.api.command.action

import cloud.commandframework.Command
import cloud.commandframework.context.CommandContext
import org.screamingsandals.lib.sender.CommandSenderWrapper

abstract class AbstractSubAction(parentAction: AbstractAction) {
    protected val gameManager = parentAction.gameManager
    protected val commandManager = parentAction.commandManager
    protected val mainCommandBuilder = parentAction.mainCommandBuilder

    abstract fun build(builder: Command.Builder<CommandSenderWrapper>)

    protected fun handleCommand(context: CommandContext<Any>) {
        //To be implemented, but not needed
    }
}