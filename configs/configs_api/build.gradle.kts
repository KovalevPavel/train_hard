plugins {
    id("trainhard.android.library")
}

dependencies {
    implementation(project(":core_domain"))
    implementation(project(":configs_data"))
    api(project(":configs_core"))

    implementation(libs.koin.core)
}
