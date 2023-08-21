plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
}

android {
    ksp {
        arg("compose-destinations.mode", "navgraphs")
        arg("compose-destinations.moduleName", "home")
    }
}

dependencies {
    implementation(project(":home_domain"))
    implementation(project(":ui_theme"))
    implementation(project(":core_domain"))

    implementation(libs.koin.compose)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}