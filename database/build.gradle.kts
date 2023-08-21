plugins {
    id("trainhard.android.library")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    defaultConfig {
        kapt {
            arguments { arg("room.schemaLocation", "$projectDir/build/room/schemas") }
        }
    }
}

dependencies {
    implementation(libs.room.common)
    ksp(libs.room.kapt)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(project(":database_api"))

    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)

    implementation(libs.koin.compose)
}
