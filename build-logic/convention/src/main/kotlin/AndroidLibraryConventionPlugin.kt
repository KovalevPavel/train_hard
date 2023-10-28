import com.android.build.gradle.LibraryExtension
import consts.Config
import consts.Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import utils.addDependencies
import utils.configureKotlinAndroid

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
