package com.devundefined.rijksmuseumsample.ui.artdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devundefined.rijksmuseumsample.R
import com.devundefined.rijksmuseumsample.domain.model.ArtItemDetails
import com.devundefined.rijksmuseumsample.ui.theme.RijksmuseumSampleTheme

@Composable
fun ArtDetailsScreen(state: ArtDetailsScreenState, onBack: () -> Unit, retryAction: () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.primary_light))
            .fillMaxSize()
    ) {
        when (state) {
            ArtDetailsScreenState.Failure -> FailedContent(retryAction)
            ArtDetailsScreenState.Loading -> LoadingContent()
            is ArtDetailsScreenState.ScreenData -> ArtDetailsCard(state.data)
        }

        IconButton(
            onClick = onBack, modifier = Modifier
                .padding(18.dp)
                .clip(CircleShape)
                .background(
                    color = colorResource(
                        id = R.color.primary_most_light
                    )
                )
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                tint = colorResource(id = R.color.primary_most_dark)
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        PlaceHolder()
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PlaceHolder() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(300.dp, 300.dp),
        painter = painterResource(id = R.drawable.image_placeholder),
        contentDescription = "Placeholder"
    )
}

@Composable
@Preview
fun LoadingPreview() {
    RijksmuseumSampleTheme {
        ArtDetailsScreen(state = ArtDetailsScreenState.Loading, {}, {})
    }
}

@Composable
fun ArtDetailsCard(artItemDetails: ArtItemDetails) {
    LazyColumn {
        item {
            if (artItemDetails.image != null) {
                Image(
                    painter = rememberImagePainter(
                        data = artItemDetails.image.url,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.image_placeholder_with_background)
                            size(OriginalSize)
                        }
                    ),
                    contentDescription = "Art item image for object ${artItemDetails.title}",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            } else {
                PlaceHolder()
            }
        }
        item {
            Text(
                text = artItemDetails.title,
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                color = colorResource(id = R.color.primary_most_dark),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = artItemDetails.description,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                color = colorResource(id = R.color.primary_most_dark)
            )
        }
    }
}

@Composable
fun FailedContent(retryAction: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 50.dp),
                text = "Error occurs!\nCheck internet connection and retry again please.",
                fontSize = 24.sp,
                color = colorResource(id = R.color.primary_most_dark),
                textAlign = TextAlign.Center,
            )
            Button(onClick = retryAction, modifier = Modifier.padding(16.dp)) {
                Text(text = "RETRY")
            }
        }
    }
}

@Composable
@Preview
fun FailedPreview() {

}

@Preview
@Composable
fun ArtDetailsScreenPreview() {
    //ArtDetailsScreen(itemId = "Sample item id") { }
}