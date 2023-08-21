plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
}

android {
    ksp {
        arg("compose-destinations.mode", "navgraphs")
        arg("compose-destinations.moduleName", "parameters")
    }
}

dependencies {
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}