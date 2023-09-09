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
    implementation(project(":components"))
    implementation(project(":parameters_domain"))
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
