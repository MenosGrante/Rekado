import java.util.*

/**
 * Check if dependency version is non-stable (alpha, beta, RC, etc.).
 */
fun String.isDependencyNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA", "SNAPSHOT").any { toUpperCase(Locale.ROOT).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return isStable.not()
}