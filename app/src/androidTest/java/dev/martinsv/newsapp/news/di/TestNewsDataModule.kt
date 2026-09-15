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

    // Tests inject this to control the data, for example to set an error
    @Provides
    @Singleton
    fun provideFakeNewsRepository(): FakeNewsRepository = FakeNewsRepository()

    @Provides
    fun provideNewsRepository(fakeRepository: FakeNewsRepository): NewsRepository = fakeRepository
}