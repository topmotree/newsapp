package dev.martinsv.newsapp.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.martinsv.newsapp.news.data.repository.FakeNewsRepository
import dev.martinsv.newsapp.news.domain.NewsRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewsDataModule::class]
)
object TestNewsDataModule {

    @Provides
    @Singleton
    fun provideNewsRepository(): NewsRepository = FakeNewsRepository()
}