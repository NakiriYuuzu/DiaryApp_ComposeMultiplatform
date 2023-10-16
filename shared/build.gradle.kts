plugins {
    kotlin("multiplatform")
    id("com.android.library")

    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version Versions.kotlin
    id(Deps.SqlDelight.sqlDelightPlugin) version Versions.sqlDelight
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose Multiplatform
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                with(Deps.Kotlinx) {
                    api(dateTime)
                    api(coroutines)
                }
                with(Deps.Precompose) {
                    api(precompose)
                    api(precomposeKoin)
                    api(precomposeViewModel)
                }
                with(Deps.SqlDelight) {
                    api(runtime)
                    api(coroutinesExtensions)
                }
                with(Deps.Ktor) {
                    api(core)
                    api(logging)
                    api(negotiation)
                    api(serialization)
                }
                implementation(Deps.Koin.core)
                implementation(Deps.Calf.filePicker)
                implementation(Deps.MoriMushi.insetsX)
                implementation(Deps.Moko.Location.location)
                implementation(Deps.Moko.Biometry.biometry)
                implementation(Deps.Moko.Permissions.permissions)
                implementation(Deps.SharedPreferences.multiplatformArgs)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                with(Deps.Androidx) {
                    api(core)
                    api(appcompat)
                    api(activityCompose)
                }
                implementation(Deps.Ktor.ktorAndroid)
                implementation(Deps.SqlDelight.androidDriver)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Deps.Ktor.ktorIOS)
                implementation(Deps.SqlDelight.iosDriver)
            }
        }
    }
}

android {
    namespace = Configurations.applicationId
    compileSdk = Configurations.compileSdk
    defaultConfig {
        minSdk = Configurations.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlin {
        jvmToolchain(18)
    }
}
