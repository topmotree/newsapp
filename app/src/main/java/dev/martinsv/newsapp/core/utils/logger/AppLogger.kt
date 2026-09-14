package dev.martinsv.newsapp.core.utils.logger

interface AppLogger {
    fun d(tag: String = DEFAULT_LOG_TAG, message: () -> String)
    fun i(tag: String = DEFAULT_LOG_TAG, message: () -> String)
    fun w(tag: String = DEFAULT_LOG_TAG, message: () -> String)
    fun e(tag: String = DEFAULT_LOG_TAG, error: Throwable? = null, message: () -> String)
}

const val DEFAULT_LOG_TAG = "news_app"

/**
 * Use this only where you can't inject the logger through the constructor
 */
val logger: AppLogger = AndroidLogger()