package com.devundefined.rijksmuseumsample.data

import com.devundefined.rijksmuseumsample.data.dto.ArtDetailsDto
import com.devundefined.rijksmuseumsample.data.dto.CollectionDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksmuseumApi {

    @GET("api/en/collection?imgonly=True")
    suspend fun getCollection(
        @Query("key") key: String,
        @Query("p") pageNumber: Int,
        @Query("ps") itemsPerPage: Int,
        @Query("s") sort: String,
        @Query("material") material: String,
    ): CollectionDto

    @GET("/api/en/collection/{id}")
    suspend fun getArtDetails(
        @Path("id") itemNumber: String,
        @Query("key") key: String
    ): ArtDetailsDto

    companion object {
        const val BASE_URL = "https://www.rijksmuseum.nl/"
    }
}