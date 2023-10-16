package net.yuuzu.diaryapp_composemultiplatform.presentation.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType

@Composable
fun AddImage(
    imageType: ImageType,
    iconSize: Dp = 48.dp,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = FilterQuality.Low,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        when (imageType) {
            is ImageType.BitmapType -> {
                Image(
                    bitmap = imageType.bitmap,
                    contentDescription = null,
                    contentScale = contentScale,
                    filterQuality = filterQuality,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onClicked() }
                )
            }
            is ImageType.VectorType -> {
                Icon(
                    imageVector = imageType.imageVector,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(iconSize)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable { onClicked() }
                        .padding(8.dp)
                )
            }
        }
    }
}