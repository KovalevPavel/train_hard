plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":core_dialogs"))
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
    implementation(project(":new_training_domain"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))
    implementation(project(":configs_api"))
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.koin.compose)
    implementation(libs.navigation)
}
