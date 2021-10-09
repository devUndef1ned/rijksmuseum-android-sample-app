package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.data.NetworkLoadService
import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollectionDataProvider @Inject constructor(
    private val mutableState: CollectionDataMutableState,
    private val loadService: NetworkLoadService,
    private val stateReducer: StateReducer,
) {

    private val state: MutableStateFlow<CollectionState> by mutableState::state

    suspend fun initialLoading() {
        state.value = CollectionState.InitialLoading
        withContext(Dispatchers.IO) {
            val networkResult = loadService.loadPage(0)
            state.value = stateReducer.reduceState(state.value, networkResult)
        }
    }
}