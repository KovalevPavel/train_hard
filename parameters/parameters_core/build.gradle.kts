plugins {
    id("trainhard.kotlin.library")
}

dependencies {
    api(project(":database_api"))
    api(project(":core_domain"))
}
