plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":new_training_presentation"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation)
}
