package piuk.blockchain.androidcore.utils.helperfunctions

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function [initializer]
 * but takes no thread-safety mode, instead using [LazyThreadSafetyMode.NONE]. This is for
 * performance reasons, as unnecessarily creating a thread-safe instance can be quite expensive.
 *
 * If the initialization of a value throws an exception, it will attempt to reinitialize the value
 * at next access.
 *
 * @param initializer The initialization function
 */
fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)