package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import com.devundefined.rijksmuseumsample.domain.model.PageData

class StateReducer {
    fun reduceState(
        currentState: CollectionState,
        networkResult: Result<PageData>
    ): CollectionState {
        return when (currentState) {
            is CollectionState.CollectionData -> reduceDataState(currentState, networkResult)
            is CollectionState.DataWithFailure -> reduceDataState(
                currentState.currentData,
                networkResult
            )
            CollectionState.EmptyResult -> reduceInitial(networkResult)
            is CollectionState.InitialFailure -> reduceInitial(networkResult)
            CollectionState.InitialLoading -> reduceInitial(networkResult)
        }
    }

    private fun reduceInitial(networkResult: Result<PageData>): CollectionState {
        return if (networkResult.isSuccess) {
            val networkData = networkResult.getOrThrow()
            if (networkData.data.isEmpty() && networkData.pageNumber == 0) {
                CollectionState.EmptyResult
            } else {
                CollectionState.CollectionData(
                    networkData.data.groupBy(ArtItem::principalOrFirstMaker),
                    networkData.containsMore
                )
            }
        } else {
            CollectionState.InitialFailure(
                networkResult.exceptionOrNull() ?: IllegalStateException(
                    "Unknown error"
                )
            )
        }
    }

    private fun reduceDataState(
        currentState: CollectionState.CollectionData,
        networkResult: Result<PageData>
    ): CollectionState {
        return if (networkResult.isSuccess) {
            val resultData = networkResult.getOrThrow()
            val newList =
                currentState.dataMap.entries.fold(emptyList<ArtItem>()) { acc, entry -> acc + entry.value } + resultData.data
            CollectionState.CollectionData(
                newList.groupBy(ArtItem::principalOrFirstMaker),
                containsMore = resultData.containsMore
            )
        } else {
            CollectionState.DataWithFailure(
                currentState,
                networkResult.exceptionOrNull() ?: IllegalStateException("Unknown error")
            )
        }
    }
}