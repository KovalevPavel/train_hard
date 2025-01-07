plugins {
    id("trainhard.android.application")
    id("trainhard.android.compose")
}

android {
    val majorVersion = 1
    val minorVersion = 0
    val patchVersion = 0

    buildFeatures {
        buildConfig = true
    }

    version = "$majorVersion.$minorVersion.$patchVersion"
    project.setProperty("archivesBaseName", "TrainHard-$version")

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
    implementation(libs.navigation)
    implementation(project(":home_api"))
    implementation(project(":statistics_api"))
    implementation(project(":design_system"))

    implementation(project(":core"))
    implementation(project(":core_domain"))
    implementation(project(":core_dialogs"))
    implementation(project(":core_storage"))
    implementation(project(":ui_theme"))
    implementation(project(":database"))
    implementation(project(":database_api"))
    implementation(project(":navigation"))
    implementation(project(":new_training_api"))
    implementation(project(":training_calendar_api"))
    implementation(project(":parameters_api"))

    implementation(libs.room.ktx)

    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.logging.timber)
    implementation(libs.accompanist.systemuicontroller)
}
