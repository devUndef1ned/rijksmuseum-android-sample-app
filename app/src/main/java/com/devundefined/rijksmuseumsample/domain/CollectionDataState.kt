package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import kotlinx.coroutines.flow.StateFlow

interface CollectionDataState {
    val state: StateFlow<CollectionState>
}