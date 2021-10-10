package com.devundefined.rijksmuseumsample.ui

sealed class Screen(val screenName: String) {


    object CollectionScreen : Screen("collection")
    object ArtDetailsScreen : Screen("artDetails") {
        fun createRoute(itemId: String) = "artDetails/$itemId"
    }

    companion object {
        const val DETAILS_SCREEN_ARG_KEY: String = "artItemId"
    }
}