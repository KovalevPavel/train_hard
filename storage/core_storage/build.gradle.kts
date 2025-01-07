plugins {
    id("trainhard.android.library")
}

dependencies {
    implementation(project(":core_domain"))
    implementation(project(":domain_storage"))
    api(libs.paper)
    implementation(libs.androidx.core.ktx)
    implementation(libs.koin.compose)
}
