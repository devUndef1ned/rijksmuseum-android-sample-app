package com.devundefined.rijksmuseumsample.domain.model

import com.devundefined.rijksmuseumsample.domain.model.ArtItem

data class PageData(val data: List<ArtItem>, val count: Int, val pageNumber: Int, val containsMore: Boolean)