package com.devundefined.rijksmuseumsample.ui.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import coil.size.Size
import coil.size.SizeResolver
import com.devundefined.rijksmuseumsample.R
import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.ImageSpec
import com.devundefined.rijksmuseumsample.ui.theme.RijksmuseumSampleTheme


@Composable
fun CollectionScreen(state: CollectionScreenState) {
    when (state) {
        is CollectionScreenState.ScreenData -> ArtList(items = state.items)
        is CollectionScreenState.Failure -> Text("Failure occurs.\n${state.e.localizedMessage}")
        CollectionScreenState.Loading -> CircularProgressIndicator()
    }

}

@Composable
fun ArtList(items: List<CollectionScreenItem>) {
    if (items.isEmpty()) {
        Text(text = "Empty result")
    } else {
        LazyColumn {
            this.items(items) { item ->
                when (item) {
                    is CollectionScreenItem.ArtScreenItem -> ArtItemRow(item = item.artItem)
                    is CollectionScreenItem.AuthorItem -> AuthorRow(item.author.capitalize(Locale.current))
                    CollectionScreenItem.FailedLoadingIndicator -> Text(text = "Loading failed")
                    CollectionScreenItem.LoadingIndicator -> CircularProgressIndicator()
                }

            }
        }
    }
}

@Composable
fun AuthorRow(authorName: String) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 0.dp)
            .widthIn(max = 300.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .background(color = colorResource(id = R.color.primary_dark)),

    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = authorName,
            fontSize = 30.sp,
            color = Color.White
        )
    }
}

@Composable
fun ArtItemRow(item: ArtItem) {
    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
        Image(
            painter = rememberImagePainter(
                data = item.headerImage.url,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.placeholder_background)
                    size(OriginalSize)
                }
            ),
            contentDescription = "Image description",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart),
            color = Color.White,
            fontSize = 24.sp,
            text = item.title,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun ArtItemListPreview() {
    ArtList(items = artItemList)
}

@Preview
@Composable
fun ArtItemPreview() {
    ArtItemRow(item = artItem)
}

private val artItem = ArtItem(
    id = "en-SK-A-1412",
    objectNumber = "SK-A-1412",
    title = "Matthias, Emperor of the Holy Roman Empire (1557-1619)",
    hasImage = true,
    principalOrFirstMaker = "Hans von Aachen",
    headerImage = ImageSpec(
        width = 1920,
        height = 460,
        url = "https://lh4.ggpht.com/ZMBDOxcEqfDeU2LykL-XoEW5r5o6gZJ2HgVBk2ortU6Pl0tl3iZelv6jJHgnJ8GWHH2Ua4RAqN_DKJLj631AzhTkLHc=s0"
    ),
    image = ImageSpec(
        width = 1974,
        height = 2500,
        url = "https://lh4.ggpht.com/XnJSWZGE0GWxzWTj15CfHcyXWyjRw23TNcxiPKi7D2ykLlVX5gvWk41PUuNcggM6F2ZLDZbugAp2X8E7FslKR-giOsB2=s0"
    )
)

private val artItemList = listOf(
    CollectionScreenItem.AuthorItem(artItem.principalOrFirstMaker),
    CollectionScreenItem.ArtScreenItem(
        artItem
    )
)