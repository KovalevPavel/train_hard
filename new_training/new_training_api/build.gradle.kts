plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":navigation"))
            implementation(project(":new_training_presentation"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation)
        }
    }
}
