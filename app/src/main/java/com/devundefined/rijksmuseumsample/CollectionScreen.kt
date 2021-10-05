package com.devundefined.rijksmuseumsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devundefined.rijksmuseumsample.domain.ArtItem

@Composable
fun CollectionScreen(items: List<ArtItem>) {
    LazyColumn(verticalArrangement = Arrangement.Center) {
        this.items(items) { item ->
            ArtItemRow(item = item)
        }
    }
}

@Composable
fun ArtItemRow(item: ArtItem) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.image_placeholder),
            contentDescription = "Image description"
        )
        Text(text = item.title)
    }
}