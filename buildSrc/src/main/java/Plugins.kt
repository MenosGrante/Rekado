object Plugins {

    val GradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.Gradle.Plugin}" }
    val Kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.Kotlin}" }
    val Hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.AndroidX.Hilt}" }

}