plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling)
        }

        commonMain.dependencies {
            implementation(project(":core_domain"))
            implementation(project(":navigation"))
            implementation(project(":ui_theme"))
            implementation(project(":design_system"))

            implementation(libs.koin.compose)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
