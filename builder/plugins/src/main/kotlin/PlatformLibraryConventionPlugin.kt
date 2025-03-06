import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.configAndroid
import utils.kotlin
import utils.libs
import utils.nativeTargets
import utils.projectJvmTarget

class PlatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
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
            }
        }
    }
}
