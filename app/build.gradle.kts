
plugins {
    id("trainhard.android.application")
    id("trainhard.android.compose")
    alias(libs.plugins.google.ksp)
}

android {
    ksp {
        arg("compose-destinations.generateNavGraphs", "false")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = getByName("debug").signingConfig
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splash)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)

    implementation(libs.destinations.core)

    implementation(project(":components"))
    implementation(project(":core_domain"))
    implementation(project(":core_dialogs"))
    implementation(project(":core_storage"))
    implementation(project(":ui_theme"))
    implementation(project(":database"))
    implementation(project(":database_api"))
    implementation(project(":navigation"))
    implementation(project(":new_training_api"))
    implementation(project(":new_training_presentation"))
    implementation(project(":training_calendar_api"))
    implementation(project(":training_calendar_presentation"))

    implementation(project(":home_presentation"))
    implementation(project(":statistics_presentation"))
    implementation(project(":parameters_api"))
    implementation(project(":parameters_presentation"))

    implementation(libs.room.ktx)

    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.logging.timber)
    implementation(libs.accompanist.systemuicontroller)
}
