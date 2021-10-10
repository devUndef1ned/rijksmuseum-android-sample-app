package com.devundefined.rijksmuseumsample.domain.model

data class ArtItemDetails(
    val id: String,
    val objectNumber: String,
    val title: String,
    val image: ImageSpec?,
    val description: String,
    val principalMaker: String
)