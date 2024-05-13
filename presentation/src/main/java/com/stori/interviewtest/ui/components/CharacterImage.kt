package com.stori.interviewtest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

private val defaultModifier = Modifier
    .fillMaxWidth()
    .size(200.dp)

@Composable
fun CharacterImage(imageUrl: String, modifier: Modifier = defaultModifier) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Character image",
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loading = { LoadingState() }
    )
}

@Preview
@Composable
private fun CharacterImagePreview() {
    CharacterImage(
        imageUrl = "image url",
        modifier = defaultModifier.then(
            Modifier.background(
                brush = Brush.verticalGradient(listOf(Color.White, Color.Black))
            )
        )
    )
}
