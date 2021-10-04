package com.devundefined.rijksmuseumsample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionDto(val count: Int, val artObjects: List<ArtObjectDto>)