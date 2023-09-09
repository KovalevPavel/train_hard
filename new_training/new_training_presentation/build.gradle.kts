plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
    id("kotlin-parcelize")
}

android {
    ksp {
        arg("compose-destinations.mode", "destinations")
    }
}

dependencies {
    implementation(project(":ui_theme"))
    implementation(project(":new_training_domain"))
    implementation(project(":new_training_api"))
    implementation(project(":components"))
    implementation(project(":navigation"))

    implementation(libs.koin.compose)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
