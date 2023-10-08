@file:Suppress("UnstableApiUsage")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

fun withDirectory(dir: String, action: File.() -> Unit) {
    file("$rootDir/$dir").apply(action)
}

fun File.includeProject(moduleName: String) {
    include(":$moduleName")
    project(":$moduleName").projectDir = file("${this.path}/$moduleName")
}

rootProject.name = "Train hard"

include(":app")
include(":core_domain")
include(":database")
include(":database_api")
include(":navigation")
include(":ui_theme")
include(":components")
include(":core_design")
include(":core_dialogs")
include(":core_presentation")

withDirectory("home") {
    includeProject("home_presentation")
    includeProject("home_domain")
}

withDirectory("new_training") {
    includeProject("new_training_api")
    includeProject("new_training_domain")
    includeProject("new_training_presentation")
}

withDirectory("statistics") {
    includeProject("statistics_presentation")
}

withDirectory("parameters") {
    includeProject("parameters_api")
    includeProject("parameters_domain")
    includeProject("parameters_presentation")
}

withDirectory("storage") {
    includeProject("domain_storage")
    includeProject("core_storage")
}

withDirectory("training_calendar") {
    includeProject("training_calendar_api")
    includeProject("training_calendar_domain")
    includeProject("training_calendar_presentation")
}
