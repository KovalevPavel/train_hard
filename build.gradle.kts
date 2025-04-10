// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

apply(from = "$rootDir/buildConfig.gradle.kts")
