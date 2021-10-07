package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.model.ArtItem

sealed class CollectionScreenState {
    object Loading : CollectionScreenState()
    object Empty : CollectionScreenState()
    class Failure(val e: Throwable) : CollectionScreenState()
    class ScreenData(val items: List<CollectionScreenItem>)  : CollectionScreenState()
}

sealed class CollectionScreenItem {
    class AuthorItem(val author: String) : CollectionScreenItem()
    class ArtScreenItem(val artItem: ArtItem, val author: String) : CollectionScreenItem()
    object LoadingIndicator : CollectionScreenItem()
    object FailedLoadingIndicator : CollectionScreenItem()
}