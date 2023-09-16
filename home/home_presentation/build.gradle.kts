plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
}

android {
    ksp {
        arg("compose-destinations.mode", "destinations")
    }
}

dependencies {
    implementation(project(":ui_theme"))
    implementation(project(":home_domain"))
    implementation(project(":core_domain"))
    implementation(project(":core_presentation"))
    implementation(project(":components"))
    implementation(project(":navigation"))
    implementation(project(":new_training_api"))

    implementation(libs.koin.compose)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}