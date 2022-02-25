package net.hoz.gamecore.api

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
    fun toBuilder(builder: B.() -> Unit): B
}