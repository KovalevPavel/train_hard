plugins {
    id("th.platform.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.ktx)
        }

        commonMain.dependencies {
            implementation(project(":core_domain"))
            implementation(project(":core"))
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.navigation)
        }
    }
}
