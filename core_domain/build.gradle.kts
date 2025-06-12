plugins {
    id("th.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlinx.datetime)
        }
    }
}
