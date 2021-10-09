package com.devundefined.rijksmuseumsample.domain.model

sealed class CollectionState {
    object InitialLoading : CollectionState()
    object EmptyResult : CollectionState()
    class InitialFailure(val failure: Throwable) : CollectionState()
    class CollectionData(val dataMap: Map<String, List<ArtItem>>, val containsMore: Boolean) : CollectionState()
    class DataWithFailure(val currentData: CollectionData, val failure: Throwable) : CollectionState()
}