package com.devundefined.rijksmuseumsample.ui.artdetails

import com.devundefined.rijksmuseumsample.domain.model.ArtItemDetails

sealed class ArtDetailsScreenState {
    object Loading : ArtDetailsScreenState()
    object Failure : ArtDetailsScreenState()
    class ScreenData(val data: ArtItemDetails) : ArtDetailsScreenState()
}