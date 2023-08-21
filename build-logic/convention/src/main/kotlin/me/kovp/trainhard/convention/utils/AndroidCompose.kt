package me.kovp.trainhard.convention.utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *>) {
    commonExtension.apply {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        defaultConfig {
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
        }

        addDependencies(
            libs,
            listOf(
                "androidx-compose-ui",
                "androidx-compose-ui-tooling-preview",
                "androidx-compose-material",
            ),
        )
    }
}

private fun Project.addDependencies(
    versionCatalog: VersionCatalog,
    libs: List<String>,
) {
    dependencies {
        libs.forEach {
            add("implementation", versionCatalog.findLibrary(it).get())
        }
    }
}
