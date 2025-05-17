import org.gradle.api.Project
import utils.AbstractComposeConventionPlugin
import utils.kotlin
import utils.libs

class PlatformComposeConventionPlugin : AbstractComposeConventionPlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        with(target) {
            pluginManager.apply {
                apply(libs.plugins.composeMultiplatform.get().pluginId)
                apply(libs.plugins.composeCompiler.get().pluginId)
            }

            kotlin {
                sourceSets.findByName("commonMain")?.dependencies {
                    implementation(compose.runtime)
                    implementation(compose.material3)
                    implementation(compose.ui)
                    implementation(compose.components.resources)
                    implementation(compose.components.uiToolingPreview)
                    implementation(project(":core"))
                }
            }
        }
    }
}
