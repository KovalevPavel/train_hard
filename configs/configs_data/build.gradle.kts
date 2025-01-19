plugins {
    id("trainhard.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core_domain"))
    implementation(project(":domain_storage"))
    implementation(project(":configs_core"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.annotation.jvm)
}
