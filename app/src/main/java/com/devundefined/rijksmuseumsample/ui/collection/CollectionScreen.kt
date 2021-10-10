package com.devundefined.rijksmuseumsample.ui.collection

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devundefined.rijksmuseumsample.R
import com.devundefined.rijksmuseumsample.domain.model.ArtItem
import com.devundefined.rijksmuseumsample.domain.model.ImageSpec

@Composable
fun CollectionScreen(
    state: CollectionScreenState,
    loadMoreAction: () -> Unit = {},
    clickItemAction: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier.background(color = colorResource(id = R.color.primary_most_light))
    ) {
        when (state) {
            is CollectionScreenState.ScreenData -> ArtList(
                items = state.items,
                loadMoreAction = loadMoreAction,
                clickItemAction = clickItemAction
            )
            is CollectionScreenState.Failure -> Text("Failure occurs.\n${state.e.localizedMessage}")
            CollectionScreenState.Loading -> Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }

}

@Composable
fun ArtList(
    items: List<CollectionScreenItem>,
    loadMoreAction: () -> Unit = {},
    clickItemAction: (String) -> Unit = {}
) {
    if (items.isEmpty()) {
        Text(text = "Empty result")
    } else {
        LazyColumn {
            this.itemsIndexed(items) { index, item ->
                when (item) {
                    is CollectionScreenItem.ArtScreenItem -> ArtItemRow(
                        item = item.artItem,
                        isLast = index == items.lastIndex,
                        loadMoreAction = loadMoreAction,
                        clickItemAction = clickItemAction,
                    )
                    is CollectionScreenItem.AuthorItem -> AuthorRow(item.author.capitalize(Locale.current))
                    CollectionScreenItem.FailedLoadingIndicator -> Text(text = "Loading failed")
                    CollectionScreenItem.LoadingIndicator -> Loader()
                }

            }
        }
    }
}

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ArtItemRow(
    item: ArtItem,
    isLast: Boolean,
    loadMoreAction: () -> Unit = {},
    clickItemAction: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { clickItemAction(item.objectNumber) }) {
        Image(
            painter = rememberImagePainter(
                data = item.headerImage.url,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.image_placeholder_with_background)
                    size(OriginalSize)
                }
            ),
            contentDescription = "Art item image for object ${item.title}",
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
    if (isLast) {
        loadMoreAction()
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
    ArtItemRow(item = artItem, isLast = false)
}

@Preview
@Composable
fun LoaderPreview() {
    Loader()
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
    CollectionScreenItem.ArtScreenItem(artItem)
)