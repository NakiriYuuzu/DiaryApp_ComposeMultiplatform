package net.yuuzu.diaryapp_composemultiplatform.android

import android.os.Bundle
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import net.yuuzu.diaryapp_composemultiplatform.MainView

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }
}