package com.devundefined.rijksmuseumsample.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devundefined.rijksmuseumsample.domain.CollectionDataProvider
import com.devundefined.rijksmuseumsample.domain.CollectionDataState
import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val dataProvider: CollectionDataProvider,
    collectionState: CollectionDataState,
) : ViewModel() {

    val state: Flow<CollectionScreenState> = collectionState.state.map { collectionState ->
        when (collectionState) {
            is CollectionState.DataState.CollectionData -> UiMapper.collectionDataToScreenData(
                collectionState
            )
            is CollectionState.DataState.DataWithFailure -> UiMapper.collectionDataWithFailureToScreenData(
                collectionState
            )
            is CollectionState.DataState.DataLoadingMore -> UiMapper.collectionDataLoadingMoreToScreenData(
                collectionState
            )
            is CollectionState.InitialFailure -> CollectionScreenState.Failure(collectionState.failure)
            CollectionState.InitialLoading -> CollectionScreenState.Loading
            CollectionState.EmptyResult -> CollectionScreenState.Failure(IllegalStateException("Empty result"))
        }
    }

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataProvider.initialLoading()
            }
        }
    }

    fun retry(pageNumber: Int) {
        if (pageNumber == 0) {
            load()
        } else {
            loadMore()
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataProvider.loadMore()
            }
        }
    }
}