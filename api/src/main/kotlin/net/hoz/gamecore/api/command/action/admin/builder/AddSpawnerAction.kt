package net.hoz.gamecore.api.command.action.admin.builder

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.Command
import net.hoz.gamecore.api.command.action.AbstractAction
import org.screamingsandals.lib.sender.CommandSenderWrapper

class AddSpawnerAction(parentAction: AbstractAction) : AbstractBuilderSubAction(parentAction) {

    override fun build(builder: Command.Builder<CommandSenderWrapper>) {
        commandManager
            .command(builder.literal("spawner", ArgumentDescription.of("Adds new GameSpawner."))
                .argument()
            )
    }
}