package utils

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin

abstract class AbstractComposeConventionPlugin: Plugin<Project> {
    private lateinit var composeDependencies: ComposePlugin.Dependencies

    protected val compose: ComposePlugin.Dependencies
        get() = composeDependencies

    override fun apply(target: Project) {
        composeDependencies = ComposePlugin.Dependencies(target)
    }
}
