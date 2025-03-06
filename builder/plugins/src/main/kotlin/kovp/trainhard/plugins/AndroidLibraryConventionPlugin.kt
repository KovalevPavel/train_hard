package kovp.trainhard.plugins

import com.android.build.gradle.LibraryExtension
import kovp.trainhard.plugins.consts.Config
import kovp.trainhard.plugins.consts.Plugins
import kovp.trainhard.plugins.utils.addDependencies
import kovp.trainhard.plugins.utils.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply {
                Plugins.android.let(::apply)
                Plugins.kotlinAndroid.let(::apply)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = Config.targetSdk
                this@with.configureKotlinAndroid(this)
            }

            addDependencies(
                libs,
                listOf(
                    "logging-timber",
                ),
            )

            dependencies {
                if (project.name != "core") {
                    add("implementation", project(":core"))
                }
                add("implementation", project(":core_domain"))
            }
        }
    }
}
