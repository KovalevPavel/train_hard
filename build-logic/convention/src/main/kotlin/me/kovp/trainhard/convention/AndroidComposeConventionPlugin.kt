package me.kovp.trainhard.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import me.kovp.trainhard.convention.utils.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

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
