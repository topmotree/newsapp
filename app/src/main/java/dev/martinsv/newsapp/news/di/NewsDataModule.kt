package dev.martinsv.newsapp.news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.martinsv.newsapp.news.data.repository.RetrofitNewsRepository
import dev.martinsv.newsapp.news.domain.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        repository: RetrofitNewsRepository
    ): NewsRepository
}