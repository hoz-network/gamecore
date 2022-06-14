package net.hoz.gamecore.core.module

import com.google.inject.AbstractModule
import net.hoz.gamecore.core.service.GameManagerImpl

class GameCoreModule : AbstractModule() {

    override fun configure() {
        bind(GameManagerImpl::class.java).asEagerSingleton()
    }
}