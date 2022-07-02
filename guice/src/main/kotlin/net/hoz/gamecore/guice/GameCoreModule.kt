package net.hoz.gamecore.guice

import cloud.commandframework.CommandManager
import cloud.commandframework.execution.CommandExecutionCoordinator
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import net.hoz.gamecore.api.lang.CommonLang
import net.hoz.gamecore.core.service.GameManagerImpl
import net.hoz.netapi.client.config.DataConfig
import net.hoz.netapi.client.module.ClientModule
import org.screamingsandals.lib.cloud.CloudConstructor
import org.screamingsandals.lib.cloud.extras.MinecraftExceptionHandler
import org.screamingsandals.lib.lang.Message
import org.screamingsandals.lib.sender.CommandSenderWrapper


class GameCoreModule(
    private val dataConfig: DataConfig
) : AbstractModule() {

    override fun configure() {
        install(ClientModule(dataConfig))
        bind(GameManagerImpl::class.java).asEagerSingleton()
    }

    @Singleton
    @Provides
    fun provideCommandManager(): CommandManager<CommandSenderWrapper> {

        val manager = CloudConstructor.construct(CommandExecutionCoordinator.simpleCoordinator())
        MinecraftExceptionHandler<CommandSenderWrapper>()
            .withDefaultHandlers()
            .withHandler(
                MinecraftExceptionHandler.ExceptionType.NO_PERMISSION
            ) { senderWrapper, _ ->
                Message.of(CommonLang.NO_PERMISSIONS)
                    .resolvePrefix()
                    .getForJoined(senderWrapper)
            }
        return manager
    }
}