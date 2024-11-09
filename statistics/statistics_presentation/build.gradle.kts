plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core_presentation"))
    implementation(project(":ui_theme"))
}
