plugins {
    id("trainhard.android.library")
    id("trainhard.android.compose")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core_presentation"))
    implementation(project(":core_design"))
    implementation(project(":ui_theme"))
    implementation(project(":training_calendar_domain"))
    implementation(project(":training_calendar_api"))
    implementation(project(":components"))
    implementation(project(":navigation"))
    implementation(project(":new_training_api"))

    implementation(libs.calendar)
    implementation(libs.koin.compose)
}
