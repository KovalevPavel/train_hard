plugins {
    id("th.platform.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.paper)
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.compose)
        }

        commonMain.dependencies {
            implementation(project(":core_domain"))
            implementation(project(":domain_storage"))
        }
    }
}
