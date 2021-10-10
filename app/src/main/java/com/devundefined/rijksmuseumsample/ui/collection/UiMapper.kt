package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.CollectionState

object UiMapper {

    fun collectionDataToScreenData(collectionData: CollectionState.DataState.CollectionData): CollectionScreenState {
        return CollectionScreenState.ScreenData(dataMapToScreenItems(collectionData.dataMap), collectionData.currentPage)
    }

    fun collectionDataWithFailureToScreenData(collectionDataWithFailure: CollectionState.DataState.DataWithFailure): CollectionScreenState {
        return CollectionScreenState.ScreenData(
            dataMapToScreenItems(collectionDataWithFailure.dataMap) + CollectionScreenItem.FailedLoadingIndicator,
            collectionDataWithFailure.currentPage
        )
    }

    fun collectionDataLoadingMoreToScreenData(collectionState: CollectionState.DataState.DataLoadingMore): CollectionScreenState {
        return CollectionScreenState.ScreenData(dataMapToScreenItems(collectionState.dataMap) + CollectionScreenItem.LoadingIndicator, collectionState.currentPage)
    }

    private fun dataMapToScreenItems(dataMap: Map<String, Set<ArtItem>>): List<CollectionScreenItem> {
        return dataMap.entries.fold(emptyList()) { acc, entry ->
            acc + CollectionScreenItem.AuthorItem(entry.key) + entry.value.map(CollectionScreenItem::ArtScreenItem)
        }
    }
}