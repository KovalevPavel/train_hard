package utils

import com.android.build.api.dsl.CommonExtension
import consts.Config
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        val props = trainProperties()
        val javaVersion = props["javaVersion"].toString()

        namespace = "me.kovp.$name"
        compileSdk = Config.compileSdk

        defaultConfig {
            minSdk = Config.minSdk
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(javaVersion.toInt())
            targetCompatibility = JavaVersion.toVersion(javaVersion.toInt())
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            jvmTarget = javaVersion
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("coreLibraryDesugaring", libs.findLibrary("desugar").get())
        }
    }
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
