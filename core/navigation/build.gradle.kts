plugins {
    alias(libs.plugins.rekado.android.library)
    alias(libs.plugins.rekado.android.library.compose)
    alias(libs.plugins.rekado.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.pavelrekun.rekado.core.navigation"
}

dependencies {

    // Core
    implementation(projects.core.ui)

    // Navigation 3
    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.navigation3.ui)
}
