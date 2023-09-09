package dev.hyuwah.imusic.features.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hyuwah.imusic.core.common.di.CommonProviderModule
import dev.hyuwah.imusic.features.search.data.remote.response.ITunesSearchService
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