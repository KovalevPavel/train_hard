import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.application
import utils.configAndroid
import utils.kotlin
import utils.libs
import utils.nativeTargets
import utils.projectJvmTarget

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
                    targetSdk = libs.versions.android.targetSdk.get().toInt()
                    applicationId = "kovp.trainhard"
                    versionCode = libs.versions.appVersion.build.get().toInt()
                    versionName = listOf(
                        libs.versions.appVersion.major.get(),
                        libs.versions.appVersion.minor.get(),
                        libs.versions.appVersion.patch.get(),
                    )
                        .joinToString(".")
                        .plus("(${libs.versions.appVersion.build.get()})")
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
