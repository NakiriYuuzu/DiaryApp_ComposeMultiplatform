package net.yuuzu.diaryapp_composemultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.moriatsushi.insetsx.WindowInsetsUIViewController
import moe.tlaster.precompose.PreComposeWindowHolder
import moe.tlaster.precompose.ProvidePreComposeCompositionLocals
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIViewController

@Composable
fun MainView() {
    val isDarkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
            UIUserInterfaceStyle.UIUserInterfaceStyleDark

    val holder = remember {
        PreComposeWindowHolder()
    }

    ProvidePreComposeCompositionLocals(
        holder,
    ) {
        App(
            darkTheme = isDarkTheme,
            dynamicColor = false
        )
    }
}

@Suppress("FunctionName", "unused")
fun MainViewController(): UIViewController {
    return WindowInsetsUIViewController {
        MainView()
    }
}