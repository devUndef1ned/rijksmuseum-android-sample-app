package com.devundefined.rijksmuseumsample.domain.model

sealed class CollectionState {
    object InitialLoading : CollectionState()

    object EmptyResult : CollectionState()

    class InitialFailure(val failure: Throwable) : CollectionState()

    sealed class DataState(
        val dataMap: Map<String, Set<ArtItem>>,
        val currentPage: Int,
        val totalPages: Int
    ) : CollectionState() {

        class CollectionData(
            dataMap: Map<String, Set<ArtItem>>,
            currentPage: Int,
            totalPages: Int
        ) : CollectionState.DataState(dataMap, currentPage, totalPages)

        class DataLoadingMore(
            dataMap: Map<String, Set<ArtItem>>,
            currentPage: Int,
            totalPages: Int
        ) : CollectionState.DataState(dataMap, currentPage, totalPages)

        class DataWithFailure(
            dataMap: Map<String, Set<ArtItem>>,
            currentPage: Int,
            totalPages: Int, val failure: Throwable
        ) : CollectionState.DataState(dataMap, currentPage, totalPages)
    }
}