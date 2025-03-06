import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.kotlin
import utils.libs
import utils.nativeTargets

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.kotlinMultiplatform.get().pluginId)
            }

            kotlin {
                nativeTargets()
                jvm()

                sourceSets.getByName("commonMain").dependencies {
                    implementation(libs.kotlinx.coroutines.core)
                }
            }
        }
    }
}
