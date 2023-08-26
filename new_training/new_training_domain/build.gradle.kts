plugins {
    id("trainhard.kotlin.library")
}

dependencies {
    implementation(libs.koin.core)
    api(project(":database_api"))
}
