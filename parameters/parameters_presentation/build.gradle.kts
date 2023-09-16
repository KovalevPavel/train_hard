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
    implementation(project(":core_presentation"))
    implementation(project(":components"))
    implementation(project(":navigation"))
    implementation(project(":ui_theme"))
    implementation(project(":parameters_domain"))
    implementation(project(":parameters_api"))

    implementation(libs.koin.compose)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
