plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
    implementation(project(":new_training_domain"))
    implementation(project(":new_training_api"))
    implementation(project(":design_system"))
    implementation(project(":navigation"))

    implementation(libs.koin.compose)
}
