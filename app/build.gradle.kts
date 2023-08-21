import me.kovp.trainhard.convention.consts.Config

plugins {
    id("trainhard.android.application")
    id("trainhard.android.compose")
    id("kotlin-kapt")
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "me.kovp.trainhard"

    defaultConfig {
        applicationId = "me.kovp.trainhard"
        versionCode = 1
        versionName = "1.0"

        compileSdkPreview = "UpsideDownCake"

        setProperty("archivesBaseName", "TrainHard($versionCode)_$versionName")
    }
    ksp {
        arg("compose-destinations.generateNavGraphs", "false")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(Config.javaVersion.toString()))
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

    implementation(project(":ui_theme"))
    implementation(project(":database"))
    implementation(project(":database_api"))
    implementation(project(":navigation"))
    implementation(project(":home_presentation"))
    implementation(project(":statistics_presentation"))
    implementation(project(":parameters_presentation"))

    implementation(libs.room.ktx)

    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)

    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.logging.timber)
    implementation(libs.accompanist.systemuicontroller)
}
