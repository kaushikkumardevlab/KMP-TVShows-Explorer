package com.kaushikkumardevlab.kmptvshowsexplorer.core.network.retry

import kotlinx.coroutines.delay

suspend fun <T>  retryWithBackoff(
    retries: Int = 3,
    initialDelay: Long = 300,
    maxDelay: Long = 2000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {

    var currentDelay = initialDelay

    repeat(retries - 1) {
        try {
            return block()
        } catch (e: Exception) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
    }

    return block()
}