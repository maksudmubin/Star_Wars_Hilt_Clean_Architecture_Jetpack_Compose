package com.mubin.starwars.base.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.itkacher.okprofiler.BuildConfig
import com.orhanobut.logger.Logger
import java.util.Timer
import java.util.TimerTask

var handlerDelayTimer: Timer = Timer()

val isReleaseBuild: Boolean = BuildConfig.BUILD_TYPE.equals("release", true)

val isStagingBuild: Boolean = BuildConfig.BUILD_TYPE.equals("staging", true)

val isDebugBuild: Boolean = BuildConfig.BUILD_TYPE.equals("debug", true)
fun logThis(message: Any) {
    if (isDebugBuild || isStagingBuild) {
        Logger.d(message)
    }
}

inline fun handlerPostDelayed(delay: Long, crossinline onSuccess: () -> Unit) {
    handlerDelayTimer.cancel()
    handlerDelayTimer = Timer()
    handlerDelayTimer.schedule(object : TimerTask() {
        override fun run() {
            Handler(Looper.getMainLooper()).post {
                onSuccess.invoke()
            }
        }
    }, delay)
}

/**
 * Add an action which will be invoked when the text is changing.
 *
 * @return the [SearchView.OnQueryTextListener] added to the [SearchView]
 */
inline fun EditText.doAfterTextChanged(
    delay: Long = 500,
    crossinline onTextChangedDelayed: (text: String) -> Unit,
) = doOnQueryTextListener(delay, onTextChangedDelayed)

inline fun EditText.doOnQueryTextListener(
    delay: Long,
    crossinline onTextChangedDelayed: (text: String) -> Unit,
): TextWatcher {
    val queryListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            handlerPostDelayed(delay) {
                onTextChangedDelayed.invoke(s.toString() ?: "")
            }
        }
    }
    this.addTextChangedListener(queryListener)
    return queryListener
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

