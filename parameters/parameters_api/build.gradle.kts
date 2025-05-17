plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":navigation"))
            implementation(libs.navigation)
            implementation(project(":parameters_presentation"))
            implementation(libs.kotlinx.serialization.json)
            api(project(":parameters_core"))
        }
    }
}
