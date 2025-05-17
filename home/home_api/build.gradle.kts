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
            implementation(project(":home_presentation"))
            implementation(project(":new_training_api"))
            implementation(project(":training_calendar_api"))
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
