import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "rekado.android.library")
            apply(plugin = "rekado.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")


            dependencies {
//                "implementation"(project(":core:ui"))
//                "implementation"(project(":core:designsystem"))

                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.compose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
                "implementation"(libs.findLibrary("kotlinx.serialization").get())
            }
        }
    }
}
