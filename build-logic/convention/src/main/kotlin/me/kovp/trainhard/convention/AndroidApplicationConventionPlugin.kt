package me.kovp.trainhard.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import me.kovp.trainhard.convention.consts.Config
import me.kovp.trainhard.convention.consts.Plugins
import me.kovp.trainhard.convention.utils.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                Plugins.application.let(::apply)
                Plugins.kotlinAndroid.let(::apply)
            }

            extensions.configure<BaseAppModuleExtension> {
                defaultConfig.targetSdk = Config.targetSdk

                this@with.configureKotlinAndroid(this)

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}
