plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":ui_theme"))
    implementation(project(":core_domain"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.serialization.json)
}
