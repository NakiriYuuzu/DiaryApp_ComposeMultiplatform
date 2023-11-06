package net.yuuzu.diaryapp_composemultiplatform.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType

@Composable
fun IconVertical(
    text: String,
    size: Dp = 48.dp,
    imageType: ImageType,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
    padding: Dp = 8.dp,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable { onClick() }
                .size(size)
                .padding(padding)
        ) {
            when (imageType) {
                is ImageType.BitmapType -> {
                    Image(
                        bitmap = imageType.bitmap,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                is ImageType.VectorType -> {
                    Icon(
                        imageVector = imageType.imageVector,
                        contentDescription = null,
                    )
                }
            }
        }
        Text(
            text = text,
            style = textStyle.copy(fontWeight = FontWeight.Medium),
            color = textColor,
        )
    }
}

@Composable
fun IconHorizontal(
    text: String,
    bitmap: ImageBitmap?= null,
    imageVector: ImageVector?= null,
    iconSize: Dp = 48.dp,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.clip(CircleShape)
                .background(backgroundColor)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                )
            }
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                )
            }
        }
        Text(
            text = text,
            style = textStyle.copy(fontWeight = FontWeight.Medium),
            color = textColor,
        )
    }
}