import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions") version Versions.Gradle.Updates
    id("com.google.devtools.ksp") version Versions.Kotlin.KSP
}

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath (Plugins.GradlePlugin)
        classpath (Plugins.Kotlin)
        classpath (Plugins.Hilt)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://jitpack.io") }
    }
}

// Task to check update for all dependencies (libraries/plugins/gradle version)
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {

    // Don't disable "Jacoco" updates | Don't display "non-stable" dependencies updates
    rejectVersionIf {
        candidate.module == "org.jacoco.ant" || candidate.version.isDependencyNonStable()
    }

}