plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":core_dialogs"))
    implementation(project(":core_presentation"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))
    implementation(project(":ui_theme"))
    implementation(project(":parameters_domain"))
    implementation(project(":parameters_core"))

    implementation(libs.koin.compose)
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
}
