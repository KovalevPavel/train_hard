plugins {
    id("th.platform.library")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.annotation.jvm)
        }

        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":core_domain"))
            implementation(project(":domain_storage"))
            implementation(project(":configs_core"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
