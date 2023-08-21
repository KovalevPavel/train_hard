plugins {
    id("trainhard.android.library")
    alias(libs.plugins.google.ksp)
}

dependencies {
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
}
