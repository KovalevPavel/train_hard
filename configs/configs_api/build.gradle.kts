plugins {
    id("th.platform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":core_domain"))
            implementation(project(":configs_data"))
            api(project(":configs_core"))

            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
