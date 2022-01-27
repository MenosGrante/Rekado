plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")

    kotlin("android")
    kotlin("kapt")
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
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        abortOnError = false
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
        jvmTarget = "11"
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }

}

dependencies {

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
    implementation(Libraries.AndroidX.SwipeRefreshLayout)
    implementation(Libraries.AndroidX.Hilt)

    // Design
    implementation(Libraries.Design.MaterialComponents)
    implementation(Libraries.Design.FlexboxLayout)
    implementation(Libraries.Design.Insetter)

    // Tools
    implementation(Libraries.Tools.Retrofit)
    implementation(Libraries.Tools.RetrofitMoshi)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation(Libraries.Tools.Zxing)
    implementation(Libraries.Tools.Moshi)

    // Kotlin
    implementation(Libraries.Kotlin.Kotlin)
    implementation(Libraries.Kotlin.Coroutines)

    // Compilers
    kapt(Libraries.Compilers.Hilt)
    kapt(Libraries.Compilers.Moshi)

    // Other
    coreLibraryDesugaring(Libraries.Other.Desugaring)

}