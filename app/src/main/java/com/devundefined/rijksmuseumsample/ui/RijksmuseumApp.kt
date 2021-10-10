package com.devundefined.rijksmuseumsample.ui

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreen
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreenState
import com.devundefined.rijksmuseumsample.ui.collection.CollectionViewModel

@Composable
fun RijksmuseumApp() {
    val collectionViewModel: CollectionViewModel by viewModels()
    val state by collectionViewModel.state.collectAsState(initial = CollectionScreenState.Loading)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.CollectionScreen.screenName) {
        composable(route = Screen.CollectionScreen.screenName) {
            CollectionScreen(state = state, loadMoreAction = collectionViewModel::loadMore)
        }
        composable(route = Screen.ArtDetailsScreen.screenName) {
            TODO()
        }
    }
}