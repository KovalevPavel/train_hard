plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":navigation"))
            implementation(project(":core_presentation"))
            implementation(project(":ui_theme"))
            implementation(libs.navigation)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
