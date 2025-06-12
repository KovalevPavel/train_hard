plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.androidx.compose.ui.tooling)
            implementation(libs.androidx.compose.ui.tooling.preview)
        }

        commonMain.dependencies {
            implementation(project(":core_dialogs"))
            implementation(project(":core_presentation"))
            implementation(project(":ui_theme"))
            implementation(project(":new_training_domain"))
            implementation(project(":design_system"))
            implementation(project(":navigation"))
            implementation(project(":configs_api"))
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.navigation)
        }
    }
}
