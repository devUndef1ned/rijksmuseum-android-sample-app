package com.devundefined.rijksmuseumsample.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devundefined.rijksmuseumsample.data.NetworkLoadService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val loadService: NetworkLoadService) : ViewModel() {

    private val _state: MutableStateFlow<CollectionScreenState> = MutableStateFlow(CollectionScreenState.Loading)

    val state: StateFlow<CollectionScreenState> = _state

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = loadService.loadPage(0)
                val newState = if (result.isSuccess) {
                    val (data, count) = result.getOrNull()!!.run { data to count }
                    if (data.isEmpty() || count == 0) {
                        CollectionScreenState.Empty
                    } else {
                        CollectionScreenState.ScreenData(data, count)
                    }
                } else {
                    CollectionScreenState.Failure(result.exceptionOrNull() ?: IllegalStateException("Unknown error"))
                }
                _state.value = newState
            }
        }
    }
}