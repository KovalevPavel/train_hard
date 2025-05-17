plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation)
            implementation(project(":navigation"))
            implementation(project(":statistics_presentation"))
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
