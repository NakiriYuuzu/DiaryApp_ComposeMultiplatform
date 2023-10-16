object Deps {
    object Androidx {
        const val core = "androidx.core:core:${Versions.androidx_core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.androidx_activity_compose}"
    }
    object Kotlinx {
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinx_datetime}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
    }
    object Koin { // Dependency Injection
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val compose = "io.insert-koin:koin-compose:${Versions.koinCompose}" // for precompose koin
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }
    object Precompose { // navigation & viewModel
        const val precompose = "moe.tlaster:precompose:${Versions.precompose}"
        const val precomposeViewModel = "moe.tlaster:precompose-viewmodel:${Versions.precompose}"
        const val precomposeKoin = "moe.tlaster:precompose-koin:${Versions.precompose}"
        object molecule {
            const val cashApp = "app.cash.molecule:molecule-runtime:${Versions.cashApp_molecule}"
            const val precompose = "moe.tlaster:precompose-molecule:${Versions.precompose}"
        }
    }
    object SharedPreferences { // Storage
        const val multiplatform = "com.russhwolf:multiplatform-settings:${Versions.multiplatform}"
        const val multiplatformArgs = "com.russhwolf:multiplatform-settings-no-arg:${Versions.multiplatform}"
        const val multiplatform_coroutine = "com.russhwolf:multiplatform-settings-coroutines:${Versions.multiplatform}"
    }
    object SqlDelight { // Database
        // plugin
        const val sqlDelightPlugin = "app.cash.sqldelight"
        // common
        const val runtime = "app.cash.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutinesExtensions = "app.cash.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        // engines
        const val androidDriver = "app.cash.sqldelight:android-driver:${Versions.sqlDelight}"
        const val iosDriver = "app.cash.sqldelight:native-driver:${Versions.sqlDelight}"
        // tests
        const val sqlDriver = "app.cash.sqldelight:sqlite-driver:${Versions.sqlDelight}"
    }
    object Ktor { // Network
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val serialization= "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

        // engines
        const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val ktorIOS = "io.ktor:ktor-client-darwin:${Versions.ktor}"
    }
    object MoriMushi {
        const val insetsX = "com.moriatsushi.insetsx:insetsx:${Versions.moriMoshi}"
    }
    object Moko {
        object Location { // GPS
            const val location = "dev.icerock.moko:geo-compose:${Versions.moko_Location}"
        }
        object Biometry { // FaceID, TouchID
            const val biometry = "dev.icerock.moko:biometry-compose:${Versions.moko_Biometry}"
        }
        object Resources { // Resources
            const val resources = "dev.icerock.moko:resources-compose:${Versions.moko_Resources}"
            const val resourcesTest = "dev.icerock.moko:resources-test:${Versions.moko_Resources}"
            const val resourcesClasspath = "dev.icerock.moko:resources-generator:${Versions.moko_Resources}"
        }
        object Permissions { // Permissions
            const val permissions = "dev.icerock.moko:permissions-compose:${Versions.moko_Permissions}"
            const val permissionsTest = "dev.icerock.moko:permissions-test:${Versions.moko_Permissions}"
        }
    }
    object Calf {
        const val filePicker = "com.mohamedrejeb.calf:calf-file-picker:${Versions.calf}"
    }
}