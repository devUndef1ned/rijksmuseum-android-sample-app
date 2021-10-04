package com.devundefined.rijksmuseumsample.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeaderImageDto(val width: Int, val height: Int, val url: String)