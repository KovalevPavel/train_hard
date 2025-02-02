plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":ui_theme"))
    implementation(project(":home_domain"))
    implementation(project(":core_domain"))
    implementation(project(":core_presentation"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))
    implementation(project(":new_training_api"))
    implementation(project(":training_calendar_api"))

    implementation(libs.koin.compose)
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
}