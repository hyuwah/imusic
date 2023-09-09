package dev.hyuwah.imusic.features.search.data.remote.response

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchService {

    @GET("/search")
    suspend fun search(
        @Query("term") term: String,
        @Query("media") media: String
    ): ApiResponse<SearchResponse>

}