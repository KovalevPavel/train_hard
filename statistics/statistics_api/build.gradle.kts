plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(libs.navigation)
    implementation(project(":navigation"))
    implementation(project(":statistics_presentation"))
    implementation(libs.kotlinx.serialization.json)
}
