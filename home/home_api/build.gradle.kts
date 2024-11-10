plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(libs.navigation)
    implementation(project(":home_presentation"))
    implementation(project(":new_training_api"))
    implementation(libs.kotlinx.serialization.json)
}
