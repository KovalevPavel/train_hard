package utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies
import java.util.Properties

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

fun Project.trainProperties(): Properties = when {
    TrainProps.isInitialized -> TrainProps.properties
    else -> initTrainProps()
}

private fun Project.initTrainProps(): Properties {
    TrainProps.properties.let {
        file("../build-logic/build.properties").inputStream().use(it::load)
    }
    TrainProps.isInitialized = true
    return TrainProps.properties
}
