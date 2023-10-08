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
    implementation(project(":core_design"))
    implementation(project(":ui_theme"))
    implementation(project(":training_calendar_domain"))
    implementation(project(":training_calendar_api"))
    implementation(project(":components"))
    implementation(project(":navigation"))

    implementation(libs.calendar)
    implementation(libs.koin.compose)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
