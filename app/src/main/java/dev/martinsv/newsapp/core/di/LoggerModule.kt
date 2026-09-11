package dev.martinsv.newsapp.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.martinsv.newsapp.core.utils.logger.AndroidLogger
import dev.martinsv.newsapp.core.utils.logger.AppLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun bindsLogger(androidLogger: AndroidLogger): AppLogger
}