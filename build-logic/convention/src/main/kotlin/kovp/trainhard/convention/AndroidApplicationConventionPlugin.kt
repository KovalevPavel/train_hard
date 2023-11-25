package kovp.trainhard.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import kovp.trainhard.convention.consts.Config
import kovp.trainhard.convention.consts.Plugins
import kovp.trainhard.convention.utils.TrainProps
import kovp.trainhard.convention.utils.configureKotlinAndroid
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
                    val props = TrainProps.getProperties()

                    targetSdk = Config.targetSdk

                    applicationId = props["applicationId"].toString().filterNot { it == '\"' }
                    versionCode = 1
                    versionName = "1.0"

                    setProperty("archivesBaseName", "TrainHard($versionCode)_$versionName")
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
