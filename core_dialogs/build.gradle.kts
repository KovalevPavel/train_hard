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
    implementation(project(":navigation"))
    implementation(project(":ui_theme"))
    implementation(project(":design_system"))

    implementation(libs.koin.compose)
}
