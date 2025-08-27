import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")

    kotlin("android")
}

android {
    compileSdk = Config.compileSDKVersion
    ndkVersion = Config.NDKVersion
    namespace = Config.namespace

    defaultConfig {
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

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        abortOnError = false
    }

    androidResources {
        noCompress.add("bin")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    base {
        archivesName.set("[${Config.versionName}] Rekado (${Config.versionCode})")
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
    implementation(Libraries.Tools.OkHttpLogging)
//    implementation(Libraries.Tools.Zxing)
    implementation(Libraries.Tools.Moshi)

    // Kotlin
    implementation(Libraries.Kotlin.Kotlin)
    implementation(Libraries.Kotlin.Coroutines)

    // Compilers
    ksp(Libraries.Compilers.Hilt)
    ksp(Libraries.Compilers.Moshi)

    // Other
    coreLibraryDesugaring(Libraries.Other.Desugaring)

}