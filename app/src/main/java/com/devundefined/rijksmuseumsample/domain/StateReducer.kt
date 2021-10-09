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
            is CollectionState.DataState.CollectionData -> reduceDataState(
                currentState.dataMap,
                currentState.currentPage,
                currentState.totalPages,
                networkResult
            )
            is CollectionState.DataState.DataWithFailure -> reduceDataState(
                currentState.dataMap,
                currentState.currentPage,
                currentState.totalPages,
                networkResult
            )
            is CollectionState.DataState.DataLoadingMore -> reduceDataState(
                currentState.dataMap,
                currentState.currentPage,
                currentState.totalPages,
                networkResult
            )
            is CollectionState.EmptyResult -> reduceInitial(networkResult)
            is CollectionState.InitialFailure -> reduceInitial(networkResult)
            CollectionState.InitialLoading -> reduceInitial(networkResult)
        }
    }

    private fun reduceInitial(networkResult: Result<PageData>): CollectionState {
        return if (networkResult.isSuccess) {
            val networkData = networkResult.getOrThrow()
            if (networkData.pageData.isEmpty() && networkData.totalCount == 0) {
                CollectionState.EmptyResult
            } else {
                CollectionState.DataState.CollectionData(
                    networkData.pageData.groupBy(ArtItem::principalOrFirstMaker).mapValues { it.value.toSet() },
                    0,
                    calculatePageCount(networkData.totalCount, networkData.itemsPerPage)
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

    private fun calculatePageCount(totalCount: Int, itemsPerPage: Int): Int {
        return totalCount / itemsPerPage + if (totalCount % itemsPerPage == 0) 0 else 1
    }

    private fun reduceDataState(
        dataMap: Map<String, Set<ArtItem>>,
        currentPage: Int,
        totalPages: Int,
        networkResult: Result<PageData>
    ): CollectionState {
        return if (networkResult.isSuccess) {
            val resultData = networkResult.getOrThrow()
            val newList =
                dataMap.entries.fold(emptyList<ArtItem>()) { acc, entry -> acc + entry.value } + resultData.pageData
            CollectionState.DataState.CollectionData(
                newList.groupBy(ArtItem::principalOrFirstMaker).mapValues { it.value.toSet() },
                currentPage = currentPage + 1,
                totalPages = totalPages,
            )
        } else {
            CollectionState.DataState.DataWithFailure(dataMap, currentPage, totalPages, networkResult.exceptionOrNull() ?: IllegalStateException("Unknown error"))
        }
    }
}