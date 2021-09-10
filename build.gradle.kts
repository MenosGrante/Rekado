apply(from = rootProject.file("gradle/updates.gradle"))

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath (Plugins.GradlePlugin)
        classpath (Plugins.GradleUpdates)
        classpath (Plugins.Kotlin)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://jitpack.io") }

        flatDir {
            dir("libs")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}