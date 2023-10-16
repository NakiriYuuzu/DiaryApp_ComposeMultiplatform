package net.yuuzu.diaryapp_composemultiplatform.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import net.yuuzu.diaryapp_composemultiplatform.common.ImageType

@Composable
fun TopHeader(
    name: String,
    description: String,
    profileImage: ImageBitmap?,
    onProfileClicked: () -> Unit,
    options: (@Composable () -> Unit)?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        if (profileImage != null) {
            CustomIcon(
                imageType = ImageType.BitmapType(profileImage),
                size = 40.dp,
                onClick = onProfileClicked,
            )
        } else {
            CustomIcon(
                imageType = ImageType.VectorType(Icons.Rounded.Person),
                size = 40.dp,
                tint = MaterialTheme.colorScheme.primary,
                background = MaterialTheme.colorScheme.onPrimary,
                onClick = onProfileClicked,
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        options?.invoke()
    }
}