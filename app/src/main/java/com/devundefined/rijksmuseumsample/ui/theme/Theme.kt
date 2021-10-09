package com.devundefined.rijksmuseumsample.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.devundefined.rijksmuseumsample.R

/* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/

@Composable
fun RijksmuseumSampleTheme(content: @Composable() () -> Unit) {
    val colors = lightColors(
        primary = colorResource(id = R.color.primary_dark),
        primaryVariant = colorResource(id = R.color.primary_most_dark),
        secondary = colorResource(id = R.color.primary_most_light)
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}