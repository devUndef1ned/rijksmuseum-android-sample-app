package com.devundefined.rijksmuseumsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreen
import com.devundefined.rijksmuseumsample.ui.collection.CollectionScreenState
import com.devundefined.rijksmuseumsample.ui.collection.CollectionViewModel
import com.devundefined.rijksmuseumsample.ui.theme.RijksmuseumSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksmuseumSampleTheme {

            }
        }
    }
}