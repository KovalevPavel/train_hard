package kovp.trainhard.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin(extensions: JavaPluginExtension) {
    val props = TrainProps.getProperties()
    val javaVersion = props["javaVersion"].toString()

    extensions.apply {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }

    val libs = this.extensions.getByType<VersionCatalogsExtension>().named("libs")

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = javaVersion
        }
    }

    addDependencies(
        libs,
        listOf(
            "kotlinx-coroutines-core",
        ),
    )
}
