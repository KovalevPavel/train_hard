package me.kovp.trainhard.convention.utils

import com.android.build.api.dsl.CommonExtension
import me.kovp.trainhard.convention.consts.Config
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
        namespace = "me.kovp.$name"
        compileSdk = Config.compileSdk

        defaultConfig {
            minSdk = Config.minSdk
        }

        compileOptions {
            sourceCompatibility = Config.javaVersion
            targetCompatibility = Config.javaVersion
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
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
