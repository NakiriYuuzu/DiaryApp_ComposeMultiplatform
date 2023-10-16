package net.yuuzu.diaryapp_composemultiplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.moriatsushi.insetsx.SystemBarsBehavior
import com.moriatsushi.insetsx.rememberWindowInsetsController
import moe.tlaster.precompose.navigation.rememberNavigator
import net.yuuzu.diaryapp_composemultiplatform.common.navigation.Navigation
import net.yuuzu.diaryapp_composemultiplatform.common.theme.AppTheme
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.MainScreen

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    val windowInsets = rememberWindowInsetsController()

    LaunchedEffect(Unit) {
        windowInsets?.setIsNavigationBarsVisible(false)
        windowInsets?.setIsStatusBarsVisible(true)
        windowInsets?.setSystemBarsBehavior(SystemBarsBehavior.Immersive)
    }

    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Navigation()
        }
    }
}