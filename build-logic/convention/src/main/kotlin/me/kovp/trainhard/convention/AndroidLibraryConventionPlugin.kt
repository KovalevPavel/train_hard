package me.kovp.trainhard.convention

import com.android.build.gradle.LibraryExtension
import me.kovp.trainhard.convention.consts.Config
import me.kovp.trainhard.convention.consts.Plugins
import me.kovp.trainhard.convention.utils.addDependencies
import me.kovp.trainhard.convention.utils.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
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
        }
    }
}
