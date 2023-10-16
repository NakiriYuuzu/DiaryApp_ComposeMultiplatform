package net.yuuzu.diaryapp_composemultiplatform.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ImageType {
    data class BitmapType(val bitmap: ImageBitmap) : ImageType()
    data class VectorType(val imageVector: ImageVector) : ImageType()
}
