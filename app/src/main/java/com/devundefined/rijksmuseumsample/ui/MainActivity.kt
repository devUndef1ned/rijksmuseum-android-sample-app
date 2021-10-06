package com.devundefined.rijksmuseumsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreen
import com.devundefined.rijksmuseumsample.ui.collection.CollectionViewModel
import com.devundefined.rijksmuseumsample.ui.theme.RijksmuseumSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val collectionViewModel: CollectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksmuseumSampleTheme {
                val state by collectionViewModel.state.collectAsState()
                CollectionScreen(state = state)
            }
        }
    }
}