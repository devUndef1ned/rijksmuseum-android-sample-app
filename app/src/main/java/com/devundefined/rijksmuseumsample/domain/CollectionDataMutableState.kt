package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionDataMutableState : CollectionDataState {
    override val state: MutableStateFlow<CollectionState> = MutableStateFlow(CollectionState.InitialLoading)
    val loadingMoreState: MutableStateFlow<Boolean> = MutableStateFlow(false)
}