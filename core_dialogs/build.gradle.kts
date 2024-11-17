plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    ksp {
        arg("compose-destinations.mode", "destinations")
    }
}

dependencies {
    implementation(project(":core_domain"))
    implementation(project(":navigation"))
    implementation(project(":ui_theme"))
    implementation(project(":design_system"))

    implementation(libs.koin.compose)
    implementation(libs.kotlinx.serialization.json)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
