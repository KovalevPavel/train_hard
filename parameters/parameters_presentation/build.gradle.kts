plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.compose)
        }

        commonMain.dependencies {
            implementation(project(":configs_api"))
            implementation(project(":core_dialogs"))
            implementation(project(":core_presentation"))
            implementation(project(":design_system"))
            implementation(project(":navigation"))
            implementation(project(":ui_theme"))
            implementation(project(":parameters_domain"))
            implementation(project(":parameters_core"))

            implementation(libs.navigation)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
