plugins {
    id("com.android.application")

    kotlin("android")
}

android {
    compileSdk = Config.compileSDKVersion
    ndkVersion = Config.NDKVersion

    defaultConfig {
        applicationId = "com.pavelrekun.rekado"

        minSdk = Config.minimumSDKVersion
        targetSdk = Config.targetSDKVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        isAbortOnError = false
    }

    androidResources {
        noCompress.add("bin")
    }

    buildFeatures {
        viewBinding = true
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    // Private
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

    // AndroidX
    implementation(Libraries.AndroidX.Core)
    implementation(Libraries.AndroidX.RecyclerView)
    implementation(Libraries.AndroidX.Browser)
    implementation(Libraries.AndroidX.Preferences)
    implementation(Libraries.AndroidX.ConstraintLayout)
    implementation(Libraries.AndroidX.LifecycleViewModel)
    implementation(Libraries.AndroidX.LifecycleLiveData)
    implementation(Libraries.AndroidX.LifecycleCommon)
    implementation(Libraries.AndroidX.NavigationFragment)
    implementation(Libraries.AndroidX.NavigationUI)

    // Design
    implementation(Libraries.Design.MaterialComponents)
    implementation(Libraries.Design.FlexboxLayout)
    implementation(Libraries.Design.Insetter)

    // Tools
    implementation(Libraries.Tools.EventBus)
    implementation(Libraries.Tools.Retrofit)
    implementation(Libraries.Tools.Zxing)
    implementation(Libraries.Tools.Gson)
    implementation(Libraries.Tools.AppUpdater)

    // Kotlin
    implementation(Libraries.Kotlin.Kotlin)
    implementation(Libraries.Kotlin.Coroutines)

}