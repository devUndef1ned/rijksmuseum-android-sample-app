package com.devundefined.rijksmuseumsample.domain.model

data class ArtItem(
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val headerImage: ImageSpec,
    val image: ImageSpec,
)