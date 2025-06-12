plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":configs_core"))
            implementation(project(":core_domain"))
        }
    }
}
