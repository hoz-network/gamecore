package net.hoz.gamecore.api.command

import cloud.commandframework.CommandManager
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

open class GameCoreCommand(
    protected val manager: CommandManager<CommandSenderWrapper>,
    protected val gameManager: GameManager,
    protected val commandPrefix: String
) {
}