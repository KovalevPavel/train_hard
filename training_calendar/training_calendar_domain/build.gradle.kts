plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            api(project(":database_api"))
            api(project(":core_domain"))
        }
    }
}
