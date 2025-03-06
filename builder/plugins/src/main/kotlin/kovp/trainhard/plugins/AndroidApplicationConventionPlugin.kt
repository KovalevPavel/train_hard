package kovp.trainhard.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import kovp.trainhard.plugins.consts.Config
import kovp.trainhard.plugins.consts.Plugins
import kovp.trainhard.plugins.utils.configureKotlinAndroid
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
                defaultConfig {
                    targetSdk = Config.targetSdk

                    applicationId = "trainHard"
                    versionCode = 1
                    versionName = "1.0"
                }

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
