import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin
import utils.kotlin
import utils.libs

class PlatformComposeConventionPlugin : Plugin<Project> {
    private lateinit var composeDependencies: ComposePlugin.Dependencies

    private val compose: ComposePlugin.Dependencies
        get() = composeDependencies

    override fun apply(target: Project) {
        composeDependencies = ComposePlugin.Dependencies(target)

        with(target) {
            pluginManager.apply {
                apply(libs.plugins.composeMultiplatform.get().pluginId)
                apply(libs.plugins.composeCompiler.get().pluginId)
            }

            kotlin {
                sourceSets.findByName("commonMain")?.dependencies {
                    implementation(compose.runtime)
                    implementation(compose.material)
                    implementation(compose.ui)
                    implementation(compose.components.resources)
                    implementation(compose.components.uiToolingPreview)
                }
            }
        }
    }
}
