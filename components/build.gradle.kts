plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":ui_theme"))
    implementation(project(":core_domain"))
    implementation(project(":core_design"))
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
}
