plugins {
    id("th.platform.library")
    id("th.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.ui.tooling)
            implementation(libs.androidx.compose.ui.tooling.preview)
        }

        commonMain.dependencies {
            implementation(project(":ui_theme"))
            implementation(project(":core_domain"))
            implementation(project(":configs_api"))
            implementation(libs.kotlinx.serialization.json)
            api(libs.compose.icons)
        }
    }
}
