plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
    implementation(project(":new_training_domain"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.koin.compose)
    implementation(libs.navigation)
}
