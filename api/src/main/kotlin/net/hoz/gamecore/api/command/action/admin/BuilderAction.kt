package net.hoz.gamecore.api.command.action.admin

import cloud.commandframework.Command
import cloud.commandframework.CommandManager
import net.hoz.api.data.game.ProtoWorldData
import net.hoz.gamecore.api.command.GContext
import net.hoz.gamecore.api.command.action.AbstractAction
import net.hoz.gamecore.api.command.action.admin.builder.*
import net.hoz.gamecore.api.command.argument.game.GameBuilderArgument
import net.hoz.gamecore.api.service.GameManager
import org.screamingsandals.lib.sender.CommandSenderWrapper

class BuilderAction(
    mainCommandBuilder: Command.Builder<CommandSenderWrapper>,
    commandManager: CommandManager<CommandSenderWrapper>,
    gameManager: GameManager
) : AbstractAction(mainCommandBuilder, commandManager, gameManager) {

    override suspend fun build() {
        val mainAction = buildMainAction()

        val setAction = mainAction.literal("set")
        val addAction = mainAction.literal("add")
        val setLobbyAction = setAction.literal("lobby")
        val setArenaAction = setAction.literal("arena")
        val teamAction = setAction.literal("team")

        //create/save
        CreateBuilderAction(this).build(mainAction)

        //ADD
        AddSpawnerAction(this).build(addAction)
        AddStoreAction(this).build(addAction)
        AddTeamAction(this).build(addAction)

        SetGameBorderAction(this, ProtoWorldData.WorldType.LOBBY).build(setLobbyAction)
        SetGameBorderAction(this, ProtoWorldData.WorldType.ARENA).build(setArenaAction)

    }

    fun buildMainAction(): Command.Builder<CommandSenderWrapper> {
        // TODO: lang
        return mainCommand.literal("builder", { "Entrypoint for creating Frames." })
            .argument(GameBuilderArgument.of(GContext.COMMAND_FRAME_FIELD, gameManager))
    }
}