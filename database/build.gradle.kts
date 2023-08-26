plugins {
    id("trainhard.android.library")
    alias(libs.plugins.google.ksp)
}

android {
    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(libs.room.common)
    ksp(libs.room.kapt)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(project(":database_api"))

    implementation(libs.koin.compose)
}
