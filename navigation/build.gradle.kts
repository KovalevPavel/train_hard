plugins {
    id("th.platform.library")
    id("th.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation)
        }
    }
}
