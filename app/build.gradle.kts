plugins {
    alias(libs.plugins.rekado.android.application)
    alias(libs.plugins.rekado.android.application.compose)
    alias(libs.plugins.rekado.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = libs.versions.namespace.get()
    ndkVersion = libs.versions.ndk.get()

    defaultConfig {
        applicationId = libs.versions.namespace.get()
        applicationIdSuffix = ".dev"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    androidResources {
        noCompress.add("bin")
    }

    // FIXME: Remove
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }


    base {
        val versionName = libs.versions.versionName.get()
        val versionCode = libs.versions.versionCode.get().toString().toInt()
        archivesName.set("[$versionName] Rekado ($versionCode)")
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    // Core
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.navigation)

    // Features
    implementation(projects.feature.instructions)
    implementation(projects.feature.payloads)
    implementation(projects.feature.logs)
    implementation(projects.feature.serialchecker)
    implementation(projects.feature.settings)

    // AndroidX
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.core)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.preferences)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.swiperefreshlayout)

    // Compose
    implementation(libs.androidx.activity.compose)
//    implementation(libs.compose.ui.tooling)
//    implementation(libs.compose.ui.tooling.preview)
//    implementation(libs.compose.material3)
//    implementation(libs.compose.material.icons)
//    implementation(libs.compose.activity)
//    implementation(libs.compose.navigation)


    // Design
    implementation(libs.design.material.components)
    implementation(libs.design.flexbox.layout)
    implementation(libs.design.insetter)

    // Tools
    implementation(libs.tools.retrofit)
    implementation(libs.tools.retrofit.serialization)
    implementation(libs.tools.okhttp.logging)
    implementation(libs.tools.zxing)
    implementation(libs.kotlinx.serialization)

}