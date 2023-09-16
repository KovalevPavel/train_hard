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
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
