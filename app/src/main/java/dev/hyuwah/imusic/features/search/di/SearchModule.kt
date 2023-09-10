package dev.hyuwah.imusic.features.search.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.hyuwah.imusic.core.common.di.CommonProviderModule
import dev.hyuwah.imusic.features.search.data.remote.response.ITunesSearchService
import dev.hyuwah.imusic.features.search.data.repository.SearchRepositoryImpl
import dev.hyuwah.imusic.features.search.data.controller.PlaybackControllerImpl
import dev.hyuwah.imusic.features.search.domain.repository.SearchRepository
import dev.hyuwah.imusic.features.search.domain.controller.PlaybackController
import dev.hyuwah.imusic.features.search.domain.usecase.PlaybackControllerUseCase
import dev.hyuwah.imusic.features.search.domain.usecase.PlaybackControllerUseCaseImpl
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCase
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCaseImpl
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchProviderModule {

    @Provides
    @Singleton
    fun provideItunesSearchService(
        @Named(CommonProviderModule.Name.RETROFIT_ITUNES) retrofit: Retrofit
    ): ITunesSearchService {
        return retrofit.create(ITunesSearchService::class.java)
    }

    @Provides
    @Singleton
    fun providePlaybackController(application: Application): PlaybackController {
        return PlaybackControllerImpl(application)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchBinderModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSearchMusicUseCase(impl: SearchMusicUseCaseImpl): SearchMusicUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindPlaybackControllerUseCase(impl: PlaybackControllerUseCaseImpl): PlaybackControllerUseCase
}