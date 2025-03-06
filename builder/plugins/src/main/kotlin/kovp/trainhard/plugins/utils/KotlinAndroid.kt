package kovp.trainhard.plugins.utils

import com.android.build.api.dsl.CommonExtension
import kovp.trainhard.plugins.consts.Config
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        val props = TrainProps.getProperties()
        val javaVersion = props["javaVersion"].toString()
        val appId = props["applicationId"].toString().filterNot { it == '\"' }

        namespace = "$appId.$name"
        compileSdk = Config.compileSdk

        defaultConfig {
            minSdk = Config.minSdk
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.values().first { it.name.contains(javaVersion) })
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(javaVersion.toInt())
            targetCompatibility = JavaVersion.toVersion(javaVersion.toInt())
            isCoreLibraryDesugaringEnabled = true
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("coreLibraryDesugaring", libs.findLibrary("desugar").get())
        }
    }
}
