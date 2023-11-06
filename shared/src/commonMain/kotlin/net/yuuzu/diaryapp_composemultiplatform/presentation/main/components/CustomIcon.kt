package net.yuuzu.diaryapp_composemultiplatform.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType

@Composable
fun CustomIcon(
    imageType: ImageType,
    shape: Shape = CircleShape,
    background: Color = MaterialTheme.colorScheme.onBackground,
    tint: Color = Color.Unspecified,
    size: Dp = 48.dp,
    padding: PaddingValues = PaddingValues(8.dp),
    onClick: () -> Unit,
) {
    when (imageType) {
        is ImageType.BitmapType -> {
            val bitmap = imageType.bitmap
            Image(
                bitmap = bitmap,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
                    .clip(shape)
                    .background(background)
                    .clickable { onClick() }
            )
        }
        is ImageType.VectorType -> {
            val imageVector = imageType.imageVector
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape)
                    .background(background)
                    .clickable { onClick() }
                    .padding(padding)
                    .size(size),
                tint = tint,
            )
        }
    }
}