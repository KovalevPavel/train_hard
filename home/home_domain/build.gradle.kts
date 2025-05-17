plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core_domain"))
            api(project(":database_api"))
            api(project(":domain_storage"))
        }
    }
}
