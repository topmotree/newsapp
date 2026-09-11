package dev.martinsv.newsapp.core.utils

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)