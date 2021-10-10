package com.devundefined.rijksmuseumsample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ArtDetailsDto(val artObject: ArtDetailsItemDto)

@JsonClass(generateAdapter = true)
data class ArtDetailsItemDto(
    val id: String,
    val objectNumber: String,
    val title: String,
    val webImage: ImageDto?,
    val description: String,
    val principalMaker: String,
)