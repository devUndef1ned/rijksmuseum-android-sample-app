package com.devundefined.rijksmuseumsample.ui.artdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devundefined.rijksmuseumsample.R

@Composable
fun ArtDetailsScreen(itemId: String, onBack: () -> Unit) {
    Box(modifier = Modifier.background(color = colorResource(id = R.color.primary_light))) {

        OutlinedButton(
            modifier = Modifier.padding(24.dp),
            onClick = onBack, shape = CircleShape, contentPadding = PaddingValues(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.primary_most_dark),
                backgroundColor = colorResource(id = R.color.primary_most_light)
            )
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "Back arrow icon"
            )
        }

        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(300.dp, 300.dp),
                painter = painterResource(id = R.drawable.image_placeholder),
                contentDescription = "Placeholder"
            )
            Text(
                text = "Going to show item with id $itemId",
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                color = colorResource(id = R.color.primary_most_dark)
            )
        }
    }
}

@Preview
@Composable
fun ArtDetailsScreenPreview() {
    ArtDetailsScreen(itemId = "Sample item id") { }
}