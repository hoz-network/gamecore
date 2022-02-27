package net.hoz.gamecore.api.command.action

import cloud.commandframework.Command
import cloud.commandframework.context.CommandContext
import org.screamingsandals.lib.sender.CommandSenderWrapper

abstract class AbstractSubAction(
    protected val parentAction: AbstractAction
) {
    protected val gameManager = parentAction.gameManager

    abstract fun build(builder: Command.Builder<CommandSenderWrapper>)

    protected fun handleCommand(context: CommandContext<Any>) {
        //To be implemented, but not needed
    }
}