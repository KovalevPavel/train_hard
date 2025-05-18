plugins {
    id("th.platform.library")
    id("th.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core_presentation"))
            implementation(project(":ui_theme"))
            implementation(project(":training_calendar_domain"))
            implementation(project(":design_system"))
            implementation(project(":navigation"))
            implementation(project(":new_training_api"))
            implementation(project(":configs_api"))

            implementation(libs.koin.compose)
            implementation(libs.calendar)
            implementation(libs.navigation)
        }
    }
}
