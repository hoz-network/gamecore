package net.hoz.gamecore.api.event

import mu.KotlinLogging
import org.screamingsandals.lib.event.*
import org.screamingsandals.lib.utils.ReceiverConsumer
import java.util.*
import java.util.function.Consumer

abstract class SEventHandlerFactory<W : SEvent, E : SEvent>(
    private val wrapperClass: Class<W>,
    private val eventClass: Class<E>,
    private val fireAsync: Boolean = true
) {
    private val log = KotlinLogging.logger { }
    private val eventMap: MutableMap<EventPriority, Consumer<E>> = EnumMap(EventPriority::class.java)

    init {
        log.trace("Registering event class: {} for wrapper class: {}", eventClass.simpleName, wrapperClass.simpleName)
        EventManager.getDefaultEventManager()
            .register(HandlerRegisteredEvent::class.java) { handlerEvent ->
                if (handlerEvent.eventManager != EventManager.getDefaultEventManager()) {
                    return@register
                }

                if (!eventClass.isAssignableFrom(handlerEvent.eventClass)) {
                    return@register
                }

                val priority = handlerEvent.handler.eventPriority

                if (!eventMap.containsKey(priority)) {
                    val handler: ReceiverConsumer<E> = ReceiverConsumer { event: E ->
                        if (!wrapperClass.isInstance(event)) {
                            return@ReceiverConsumer
                        }

                        val wrapped: W = wrapEvent(event, priority) ?: return@ReceiverConsumer
                        if (wrapped is Cancellable
                            && event is Cancellable
                        ) {
                            (wrapped as Cancellable).cancelled((event as Cancellable).cancelled())
                        }
                        if (fireAsync) {
                            try {
                                EventManager.getDefaultEventManager().fireEventAsync(wrapped, priority).get()
                            } catch (throwable: Throwable) {
                                log.error("There was an error firing event", throwable)
                            }
                        } else {
                            try {
                                EventManager.getDefaultEventManager().fireEvent(wrapped, priority)
                            } catch (throwable: Throwable) {
                                log.error("There was an error firing event", throwable)
                            }
                        }
                        var isCancelled = false
                        if (wrapped is Cancellable && event is Cancellable) {
                            isCancelled = (wrapped as Cancellable).cancelled()
                            (event as Cancellable).cancelled(isCancelled)
                        }
                        if (!isCancelled) {
                            postProcess(wrapped, event)
                        }
                    }
                    eventMap[handlerEvent.handler.eventPriority] = handler
                    EventManager.getDefaultEventManager().register(eventClass, handler, priority)
                }
            }
    }

    protected fun setCancelled(event: E, cancelled: Boolean) {
        if (event is Cancellable) {
            (event as Cancellable).cancelled(cancelled)
        }
    }

    protected abstract fun wrapEvent(event: E, priority: EventPriority): W?

    protected open fun postProcess(wrappedEvent: W, event: E) {
        //to be implemented, but not needed
    }
}