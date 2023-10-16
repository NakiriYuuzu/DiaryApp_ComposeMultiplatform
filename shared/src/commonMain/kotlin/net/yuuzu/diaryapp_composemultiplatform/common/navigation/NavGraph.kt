package net.yuuzu.diaryapp_composemultiplatform.common.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import net.yuuzu.diaryapp_composemultiplatform.presentation.detail.DetailScreen
import net.yuuzu.diaryapp_composemultiplatform.presentation.main.MainScreen

@Composable
fun Navigation(navigator: Navigator = rememberNavigator()) {
    NavHost(
        navigator = navigator,
        initialRoute = Screen.MainScreen.route,
    ) {
        scene(route = Screen.MainScreen.route) {
            MainScreen(navigator)
        }
        scene(route = Screen.DetailScreen.route) {
            DetailScreen(navigator)
        }
    }
}