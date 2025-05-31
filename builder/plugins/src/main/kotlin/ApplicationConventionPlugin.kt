import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.Versions
import utils.application
import utils.configAndroid
import utils.kotlin
import utils.libs
import utils.nativeTargets
import utils.projectJvmTarget

@Suppress("unused")
class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlinMultiplatform.get().pluginId)
                apply("th.compose")
            }

            configAndroid()

            application {
                defaultConfig {
                    targetSdk = Versions.Sdk.TARGET_SDK
                    applicationId = Versions.App.PACKAGE_ID
                    versionCode = Versions.App.BUILD
                    versionName = listOf(
                        Versions.App.MAJOR,
                        Versions.App.MINOR,
                        Versions.App.PATCH,
                    )
                        .joinToString(".")
                        .plus("(${Versions.App.BUILD})")
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                    }
                }
            }

            kotlin {
                androidTarget {
                    compilerOptions {
                        jvmTarget.set(projectJvmTarget)
                    }
                }

                nativeTargets()
                    .forEach { iosTarget ->
                        iosTarget.binaries.framework {
                            baseName = "ComposeApp"
                            isStatic = true
                        }
                    }

//                sourceSets.findByName("commonName")?.dependencies {
//                    implementation(project(":core_design"))
//                }
            }
        }
    }
}
