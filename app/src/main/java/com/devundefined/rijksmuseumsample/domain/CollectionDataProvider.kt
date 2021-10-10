package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.data.NetworkLoadService
import com.devundefined.rijksmuseumsample.domain.model.ArtItemDetails
import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CollectionDataProvider @Inject constructor(
    private val mutableState: CollectionDataMutableState,
    private val loadService: NetworkLoadService,
    private val stateReducer: StateReducer,
) {

    private val state: MutableStateFlow<CollectionState> by mutableState::state

    suspend fun initialLoading() {
        state.value = CollectionState.InitialLoading
        val networkResult = loadService.loadPage(0)
        state.value = stateReducer.reduceState(state.value, networkResult)
    }

    suspend fun loadMore() {
        val currentState = state.value
        if (currentState is CollectionState.DataState && state.value !is CollectionState.DataState.DataLoadingMore) {
            state.value = CollectionState.DataState.DataLoadingMore(
                dataMap = currentState.dataMap,
                currentPage = currentState.currentPage,
                totalPages = currentState.totalPages
            )
            val networkResult = loadService.loadPage(currentState.currentPage + 1)
            state.value = stateReducer.reduceState(state.value, networkResult)
        }
    }

    suspend fun loadDetails(itemNumber: String): Result<ArtItemDetails> {
        return loadService.loadDetails(itemNumber)
    }
}