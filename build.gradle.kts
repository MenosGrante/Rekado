import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale.ROOT

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.updates)
}

// Task to check update for all dependencies (libraries/plugins/gradle version)
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {

    // Don't display "Jacoco" updates | Don't display "non-stable" dependencies updates
    rejectVersionIf {
        candidate.module == "org.jacoco.ant" || candidate.version.isDependencyNonStable()
    }

}

/**
 * Check if dependency version is non-stable (alpha, beta, RC, etc.).
 */
fun String.isDependencyNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA", "SNAPSHOT")
        .any { uppercase(ROOT).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return isStable.not()
}