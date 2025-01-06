plugins {
    id("trainhard.android.library")
}

dependencies {
    implementation(project(":core_domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.navigation)
}
