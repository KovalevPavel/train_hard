plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
}
