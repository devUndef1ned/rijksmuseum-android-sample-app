package com.devundefined.rijksmuseumsample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devundefined.rijksmuseumsample.ui.artdetails.ArtDetailsScreen
import com.devundefined.rijksmuseumsample.ui.artdetails.ArtDetailsScreenState
import com.devundefined.rijksmuseumsample.ui.artdetails.ArtDetailsViewModel
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreen
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreenState
import com.devundefined.rijksmuseumsample.ui.collection.CollectionViewModel

@Composable
fun RijksmuseumApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.CollectionScreen.screenName) {

        composable(route = Screen.CollectionScreen.screenName) {
            val collectionViewModel: CollectionViewModel = hiltViewModel()
            val state by collectionViewModel.state.collectAsState(initial = CollectionScreenState.Loading)
            CollectionScreen(
                state = state,
                loadMoreAction = collectionViewModel::loadMore,
                clickItemAction = { id ->
                    navController.navigate(Screen.ArtDetailsScreen.createRoute(id))
                })
        }

        composable(
            route = "${Screen.ArtDetailsScreen.screenName}/{${Screen.DETAILS_SCREEN_ARG_KEY}}",
            arguments = listOf(navArgument(Screen.DETAILS_SCREEN_ARG_KEY) {
                nullable = false
                type = NavType.StringType
            })
        ) {
            val itemId = requireNotNull(it.arguments!!.getString(Screen.DETAILS_SCREEN_ARG_KEY))
            it.savedStateHandle.set(Screen.DETAILS_SCREEN_ARG_KEY, itemId)

            val viewModel = hiltViewModel<ArtDetailsViewModel>()
            val state by viewModel.itemDetailsState.collectAsState(initial = ArtDetailsScreenState.Loading)
            ArtDetailsScreen(
                state = state,
                onBack = navController::navigateUp,
                retryAction = viewModel::retry
            )
        }
    }
}