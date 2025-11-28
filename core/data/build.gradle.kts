plugins {
    alias(libs.plugins.rekado.android.library)
    alias(libs.plugins.rekado.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.pavelrekun.rekado.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.datastore)
}
