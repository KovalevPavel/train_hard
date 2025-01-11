@file:Suppress("unchecked_cast")

plugins {
    id("trainhard.android.application")
    id("trainhard.android.compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    buildFeatures {
        buildConfig = true
    }

    val applicationConfig = rootProject.extra["applicationConfig"] as Map<String, Any>

    defaultConfig {
        versionName = applicationConfig["versionName"].toString()
        versionCode = applicationConfig["versionCode"] as Int

        project.setProperty("archivesBaseName", "TrainHard-$versionName($versionCode)")
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
    implementation(libs.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":home_api"))
    implementation(project(":statistics_api"))
    implementation(project(":design_system"))

    implementation(project(":core"))
    implementation(project(":core_domain"))
    implementation(project(":core_dialogs"))
    implementation(project(":core_storage"))
    implementation(project(":configs_api"))
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
