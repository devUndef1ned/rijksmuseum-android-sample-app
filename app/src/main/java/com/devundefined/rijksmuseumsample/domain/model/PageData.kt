package com.devundefined.rijksmuseumsample.domain.model

data class PageData(val data: List<ArtItem>, val pageNumber: Int, val containsMore: Boolean = false)