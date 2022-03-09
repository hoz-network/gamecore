package net.hoz.gamecore.api

import com.iamceph.resulter.core.DataResultable
import org.jetbrains.annotations.ApiStatus

/**
 * Something that can be turned into a builder.
 *
 * @param <B> the builder type.
 * @param <R> the result type
 */
interface Buildable<R, B : Buildable.Builder<R>> {

    /**
     * Creates a new builder form this instance.
     *
     * @return builder with values from this instance.
     */
    fun toBuilder(builder: B.() -> Unit): B

    /**
     * Something that can be built
     */
    interface Builder<R> {
        /**
         * The build function.
         */
        @ApiStatus.Internal
        fun build(): DataResultable<R>
    }
}