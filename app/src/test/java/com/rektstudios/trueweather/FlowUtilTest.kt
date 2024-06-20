package com.rektstudios.trueweather

import androidx.annotation.VisibleForTesting
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
suspend fun <T> Flow<T>.getOrAwaitValue(
    time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS, afterCollect: suspend () -> Unit = {}
): T {
    var data: T? = null

    val job = CoroutineScope(Dispatchers.Main).launch {
        this@getOrAwaitValue.onCompletion { if (data == null) throw TimeoutException("Flow value was never emitted.") }
            .collect { value ->
                data = value
                cancel()
            }
    }

    try {
        withTimeout(timeUnit.toMillis(time)) {
            afterCollect()
            job.join()
        }
    } catch (e: TimeoutCancellationException) {
        throw TimeoutException("Flow value was never emitted.")
    } finally {
        job.cancel()
    }

    @Suppress("UNCHECKED_CAST") return data as T
}
