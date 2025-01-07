plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":navigation"))
    implementation(libs.navigation)
    implementation(project(":parameters_presentation"))
    implementation(libs.kotlinx.serialization.json)
    api(project(":parameters_core"))
}
