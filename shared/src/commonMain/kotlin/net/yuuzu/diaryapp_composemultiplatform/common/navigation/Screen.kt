package net.yuuzu.diaryapp_composemultiplatform.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String? = null,
    val navIcon: ImageVector? = null,
    val objectName: String? = null,
    val objectPath: String? = null
) {
    data object MainScreen: Screen(
        route = "main_screen",
    )
    data object DetailScreen: Screen(
        route = "detail_screen",
        objectName = "diaryId",
        objectPath = "/{diaryId}"
    )
}