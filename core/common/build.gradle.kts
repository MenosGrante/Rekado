plugins {
    alias(libs.plugins.rekado.jvm.library)
    alias(libs.plugins.rekado.hilt)
}

dependencies {
    implementation(projects.core.model)

    api(libs.kotlinx.coroutines)
    api(libs.kotlinx.collections)
}