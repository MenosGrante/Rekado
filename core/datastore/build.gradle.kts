plugins {
    alias(libs.plugins.rekado.android.library)
    alias(libs.plugins.rekado.hilt)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.pavelrekun.rekado.core.datastore"
}

dependencies {
    api(libs.androidx.datastore)
    api(projects.core.model)

    implementation(projects.core.common)

    implementation(libs.kotlinx.serialization)
}
