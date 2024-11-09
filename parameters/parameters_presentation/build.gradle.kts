plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core_dialogs"))
    implementation(project(":core_presentation"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))
    implementation(project(":ui_theme"))
    implementation(project(":parameters_domain"))

    implementation(libs.koin.compose)
}
