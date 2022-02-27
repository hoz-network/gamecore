package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.Command
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.action.AbstractSubAction
import org.screamingsandals.lib.sender.CommandSenderWrapper

class CreateBuilderAction(parentAction: AbstractAction) : AbstractSubAction(parentAction) {
    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        TODO("Not yet implemented")
    }
}