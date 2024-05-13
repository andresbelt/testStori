package com.stori.interviewtest.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.presentation.R

@Composable
@ExperimentalComposeUiApi
fun ProfileContent(
    photoFilePath: UserStori?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
        ) {
            photoFilePath?.photoUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Captured Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            } ?: run {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_camera),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = photoFilePath?.name ?: "")
            Text(text = photoFilePath?.username ?: "")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun test(){
    ProfileContent( null)
}
