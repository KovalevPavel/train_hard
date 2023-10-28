import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import utils.configureAndroidCompose

@Suppress("unused")
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<LibraryExtension>()
                ?.let { configureAndroidCompose(it) }
                ?: extensions.findByType<ApplicationExtension>()
                    ?.let { configureAndroidCompose(it) }
        }
    }
}
