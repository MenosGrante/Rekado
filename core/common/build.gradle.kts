plugins {
    alias(libs.plugins.rekado.jvm.library)
    alias(libs.plugins.rekado.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}