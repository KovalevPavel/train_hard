import org.gradle.api.Project
import utils.AbstractComposeConventionPlugin
import utils.configAndroid
import utils.kotlin
import utils.libs
import utils.nativeTargets
import utils.projectJvmTarget

@Suppress("unused")
class PlatformLibraryConventionPlugin : AbstractComposeConventionPlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlinMultiplatform.get().pluginId)
            }

            configAndroid()

            kotlin {
                androidTarget {
                    compilerOptions {
                        jvmTarget.set(projectJvmTarget)
                    }
                }

                nativeTargets().forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = project.name
                        isStatic = true
                    }
                }

                sourceSets.getByName("commonMain").dependencies {
                    implementation(compose.runtime)
                    implementation(compose.components.resources)
                    implementation(libs.kotlinx.coroutines.core)
                }
            }
        }
    }
}
