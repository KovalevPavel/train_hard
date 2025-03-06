package utils

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

internal val projectJvmTarget = JvmTarget.JVM_11

internal fun Project.kotlin(bloc: KotlinMultiplatformExtension.() -> Unit) {
    extensions.findByType<KotlinMultiplatformExtension>()?.apply(bloc)
}

internal fun Project.application(block: ApplicationExtension.() -> Unit) {
    extensions.findByType<ApplicationExtension>()?.apply(block)
}

internal fun KotlinMultiplatformExtension.nativeTargets() = listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
)
