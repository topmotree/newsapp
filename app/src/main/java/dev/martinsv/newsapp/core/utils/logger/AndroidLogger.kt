package dev.martinsv.newsapp.core.utils.logger

import android.util.Log
import javax.inject.Inject

class AndroidLogger @Inject constructor() : AppLogger {
    override fun d(tag: String, message: () -> String) {
        Log.d(tag, message())
    }

    override fun i(tag: String, message: () -> String) {
        Log.i(tag, message())
    }

    override fun w(tag: String, message: () -> String) {
        Log.w(tag, message())
    }

    override fun e(tag: String, error: Throwable?, message: () -> String) {
        Log.e(tag, message(), error)
    }
}