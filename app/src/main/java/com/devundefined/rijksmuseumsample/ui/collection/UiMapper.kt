package com.devundefined.rijksmuseumsample.ui.collection

import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.CollectionState

object UiMapper {

    fun collectionDataToScreenData(collectionData: CollectionState.CollectionData): CollectionScreenState {
        val items = dataMapToScreenItems(collectionData.dataMap)
        return when {
            collectionData.containsMore -> CollectionScreenState.ScreenData(items + CollectionScreenItem.LoadingIndicator)
            else -> CollectionScreenState.ScreenData(items)
        }
    }

    fun collectionDataWithFailureToScreenData(collectionDataWithFailure: CollectionState.DataWithFailure): CollectionScreenState {
        return CollectionScreenState.ScreenData(
            dataMapToScreenItems(collectionDataWithFailure.currentData.dataMap) + CollectionScreenItem.FailedLoadingIndicator
        )
    }

    private fun dataMapToScreenItems(dataMap: Map<String, List<ArtItem>>): List<CollectionScreenItem> {
        return dataMap.entries.fold(emptyList()) { acc, entry ->
            acc + CollectionScreenItem.AuthorItem(entry.key) + entry.value.map(CollectionScreenItem::ArtScreenItem)
        }
    }
}