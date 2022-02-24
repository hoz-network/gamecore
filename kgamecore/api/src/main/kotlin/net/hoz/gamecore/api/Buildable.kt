package net.hoz.gamecore.api

import com.iamceph.resulter.core.DataResultable

/**
 * Something that can be turned into a builder.
 *
 * @param <B> the builder type.
 */
interface Buildable<B> {
    /**
     * Creates a new builder form this instance.
     *
     * @return builder with values from this instance.
     */
    fun toBuilder(): DataResultable<B>
}