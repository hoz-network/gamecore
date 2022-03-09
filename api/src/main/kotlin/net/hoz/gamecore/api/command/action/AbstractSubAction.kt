package net.hoz.gamecore.api.command.action

import cloud.commandframework.Command
import org.screamingsandals.lib.sender.CommandSenderWrapper

abstract class AbstractSubAction(parentAction: AbstractAction) {
    protected val gameManager = parentAction.gameManager
    protected val commandManager = parentAction.commandManager
    protected val mainCommandBuilder = parentAction.mainCommand

    abstract fun build(builder: Command.Builder<CommandSenderWrapper>)
}