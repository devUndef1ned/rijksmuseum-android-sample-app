package com.devundefined.rijksmuseumsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.devundefined.rijksmuseumsample.ui.theme.RijksmuseumSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksmuseumSampleTheme {
                RijksmuseumApp()
            }
        }
    }
}