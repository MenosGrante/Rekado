plugins {
    alias(libs.plugins.rekado.android.library)
    alias(libs.plugins.rekado.android.library.compose)
}

android {
    namespace = "com.pavelrekun.rekado.core.ui"
}

dependencies {

    // Compose
    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material3)
    api(libs.compose.runtime)
    api(libs.compose.ui.util)
    
}