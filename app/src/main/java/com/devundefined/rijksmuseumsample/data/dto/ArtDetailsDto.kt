package com.devundefined.rijksmuseumsample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtDetailsDto(
    val id: String,
    val objectNumber: String,
    val title: String,
    val webImage: ImageDto,
    val description: String,
    val principalMaker: String,
)