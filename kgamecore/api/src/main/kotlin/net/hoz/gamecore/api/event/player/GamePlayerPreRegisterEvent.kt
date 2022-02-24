package net.hoz.gamecore.api.event.player

import net.hoz.gamecore.api.event.GCancellableEvent
import java.util.*

data class GamePlayerPreRegisterEvent(val uuid: UUID) : GCancellableEvent()