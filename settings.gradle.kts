@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rekado"

// Composite Build
includeBuild("build-logic")

// Preview Features
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Modules
include(":app")
//include(":core:ui")
//include(":core:common")
//include(":core:data")
//include(":core:domain")
//include(":core:preferences")
//include(":core:analytics")
//include(":core:handlers")
//include(":feature:analytics")
//include(":feature:subscriptions")
//include(":feature:widget")
//include(":feature:backups")
//include(":feature:bundle")
//include(":feature:modifysubscription")
//include(":feature:details")
//include(":feature:settings")
//include(":feature:onboard")
//include(":baselineprofile")
