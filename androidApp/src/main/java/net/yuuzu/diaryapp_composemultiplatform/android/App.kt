package net.yuuzu.diaryapp_composemultiplatform.android

import android.app.Application
import net.yuuzu.diaryapp_composemultiplatform.di.appModule
import net.yuuzu.diaryapp_composemultiplatform.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}