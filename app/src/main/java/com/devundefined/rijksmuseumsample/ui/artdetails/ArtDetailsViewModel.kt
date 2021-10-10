package com.devundefined.rijksmuseumsample.ui.artdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devundefined.rijksmuseumsample.domain.CollectionDataProvider
import com.devundefined.rijksmuseumsample.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val dataProvider: CollectionDataProvider,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val itemId = requireNotNull(savedStateHandle.get<String>(Screen.DETAILS_SCREEN_ARG_KEY))

    private val _itemDetailsState: MutableStateFlow<ArtDetailsScreenState> =
        MutableStateFlow(ArtDetailsScreenState.Loading)

    val itemDetailsState: Flow<ArtDetailsScreenState> = _itemDetailsState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        _itemDetailsState.value = ArtDetailsScreenState.Loading
        val result = dataProvider.loadDetails(itemId)
        if (result.isSuccess) {
            _itemDetailsState.value = ArtDetailsScreenState.ScreenData(result.getOrThrow())
        } else {
            _itemDetailsState.value = ArtDetailsScreenState.Failure
        }
    }

    fun retry() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (_itemDetailsState.value == ArtDetailsScreenState.Failure) {
                    loadData()
                }
            }
        }
    }
}