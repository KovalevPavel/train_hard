plugins {
    id("trainhard.kotlin.library")
}

dependencies {
    implementation(project(":core_domain"))
    api(project(":database_api"))
    api(project(":domain_storage"))
}