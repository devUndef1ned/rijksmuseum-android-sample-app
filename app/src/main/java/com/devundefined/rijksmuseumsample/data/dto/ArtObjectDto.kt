package com.devundefined.rijksmuseumsample.data.dto


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectDto(
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val headerImage: HeaderImageDto,
)