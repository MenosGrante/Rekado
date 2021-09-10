object Plugins {

    val GradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.Gradle.Plugin}" }
    val GradleUpdates by lazy { "com.github.ben-manes:gradle-versions-plugin:${Versions.Gradle.Updates}" }
    val Kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.Kotlin}" }

}