package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.ArtItem

sealed class CollectionScreenState {
    object Loading : CollectionScreenState()
    object Empty : CollectionScreenState()
    class Failure(val e: Throwable) : CollectionScreenState()
    class ScreenData(val items: List<ArtItem>, val totalCount: Int)  : CollectionScreenState()
}