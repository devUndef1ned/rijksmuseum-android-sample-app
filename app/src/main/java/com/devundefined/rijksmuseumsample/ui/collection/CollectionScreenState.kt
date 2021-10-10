package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.model.ArtItem

sealed class CollectionScreenState {
    object Loading : CollectionScreenState()
    class Failure(val e: Throwable) : CollectionScreenState()
    class ScreenData(val items: List<CollectionScreenItem>, val currentPage: Int)  : CollectionScreenState()
}

sealed class CollectionScreenItem {
    class AuthorItem(val author: String) : CollectionScreenItem()
    class ArtScreenItem(val artItem: ArtItem) : CollectionScreenItem()
    object LoadingIndicator : CollectionScreenItem()
    object FailedLoadingIndicator : CollectionScreenItem()
}