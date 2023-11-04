import consts.Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import utils.configureDetekt
import utils.configureKotlin

@Suppress("unused")
class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                Plugins.kotlin.let(::apply)
                Plugins.java.let(::apply)
                Plugins.detekt.let(::apply)
            }

            extensions.configure(::configureKotlin)
            configureDetekt()
        }
    }
}
