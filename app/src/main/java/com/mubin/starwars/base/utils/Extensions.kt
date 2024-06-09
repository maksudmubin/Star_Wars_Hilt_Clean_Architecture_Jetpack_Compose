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

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

