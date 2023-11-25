package kovp.trainhard.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies

fun Project.addDependencies(
    versionCatalog: VersionCatalog,
    libs: List<String>,
) {
    dependencies {
        libs.forEach {
            add("implementation", versionCatalog.findLibrary(it).get())
        }
    }
}
