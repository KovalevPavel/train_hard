plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core_presentation"))
    implementation(project(":core_design"))
    implementation(project(":ui_theme"))
    implementation(project(":new_training_domain"))
    implementation(project(":new_training_api"))
    implementation(project(":components"))
    implementation(project(":navigation"))

    implementation(libs.koin.compose)
}
