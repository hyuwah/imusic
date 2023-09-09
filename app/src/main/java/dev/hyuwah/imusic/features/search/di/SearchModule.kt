package dev.hyuwah.imusic.features.search.di

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
import dev.hyuwah.imusic.features.search.domain.repository.SearchRepository
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCase
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCaseImpl
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object SearchProviderModule {

    @Provides
    fun provideItunesSearchService(
        @Named(CommonProviderModule.Name.RETROFIT_ITUNES) retrofit: Retrofit
    ): ITunesSearchService {
        return retrofit.create(ITunesSearchService::class.java)
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
}