package dev.martinsv.newsapp.core.utils.logger

object TestLogger : AppLogger {
    override fun d(tag: String, message: () -> String) = Unit
    override fun i(tag: String, message: () -> String) = Unit
    override fun w(tag: String, message: () -> String) = Unit
    override fun e(tag: String, error: Throwable?, message: () -> String) = Unit
}