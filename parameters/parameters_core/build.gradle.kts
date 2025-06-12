plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":database_api"))
            api(project(":core_domain"))
        }
    }
}
