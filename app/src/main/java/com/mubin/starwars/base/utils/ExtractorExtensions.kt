package com.mubin.starwars.base.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * A generic method to execute codes from client and return an object [T] type.
 * If exception occur return null else the object [T].
 *
 * @param T Object type.
 * @param body Code block to be executed under try catch and should return [T].
 *
 * @return [T] object can be nullable.
 */
inline fun <T> executeBodyOrReturnNull(crossinline body: () -> T): T? {
    return try {
        body.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        logThis("executeBodyOrReturnNull : ${e.printStackTrace()}")
        null
    }
}

suspend inline fun <T> executeBodyOrReturnNullSuspended(
    shouldUseMainScope: Boolean = true,
    crossinline body: suspend CoroutineScope.() -> T
): T? {
    return withContext(if (shouldUseMainScope) Dispatchers.Main else Dispatchers.IO) {
        return@withContext try {
            body.invoke(this)
        } catch (e: Exception) {
            e.printStackTrace()
            logThis("executeBodyOrReturnNullSuspended : ${e.printStackTrace()}")
            null
        }
    }
}

suspend inline fun <T> executeBodyOrReturnNullSuspended(
    scope : CoroutineContext,
    crossinline body: suspend CoroutineScope.() -> T
): T? {
    return withContext(scope) {
        return@withContext try {
            body.invoke(this)
        } catch (e: Exception) {
            e.printStackTrace()
            logThis("executeBodyOrReturnNullSuspended : ${e.printStackTrace()}")
            null
        }
    }
}

inline fun <T> executeBodyOrReturnNull(
    crossinline body: () -> T,
    onError: (Exception) -> Unit
): T? {
    return try {
        body.invoke()
    } catch (e: Exception) {
        onError.invoke(e)
        logThis("executeBodyOrReturnNull : ${e.printStackTrace()}")
        null
    }
}
