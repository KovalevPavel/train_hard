plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core_domain"))
        }
    }
}
