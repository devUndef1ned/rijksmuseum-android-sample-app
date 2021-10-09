package com.devundefined.rijksmuseumsample.domain.model

data class PageData(val pageData: Set<ArtItem>, val totalCount: Int, val itemsPerPage: Int)