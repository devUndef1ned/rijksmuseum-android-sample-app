package com.devundefined.rijksmuseumsample.domain

import com.devundefined.rijksmuseumsample.domain.model.CollectionState
import com.devundefined.rijksmuseumsample.domain.model.PageData

class StateReducer {
    fun reduceState(currentState: CollectionState, networkResult: Result<PageData>): CollectionState {
        return CollectionState.InitialLoading
    }
}