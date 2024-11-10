plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":training_calendar_presentation"))
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
}
