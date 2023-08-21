import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "me.kovp.trainhard.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "trainhard.android.application"
            implementationClass = "me.kovp.trainhard.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "trainhard.android.library"
            implementationClass = "me.kovp.trainhard.convention.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "trainhard.android.compose"
            implementationClass = "me.kovp.trainhard.convention.AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "trainhard.kotlin.library"
            implementationClass = "me.kovp.trainhard.convention.KotlinLibraryConventionPlugin"
        }
    }
}
