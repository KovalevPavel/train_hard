import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    `kotlin-dsl`
}

val trainProps = Properties().apply {
    file("${rootProject.projectDir}/build.properties").inputStream().use(this::load)
}

val javaVersion = trainProps["javaVersion"].toString()

java {
    toolchain {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion.toInt()))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = javaVersion
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "trainhard.android.application"
            implementationClass = "kovp.trainhard.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "trainhard.android.library"
            implementationClass = "kovp.trainhard.convention.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "trainhard.android.compose"
            implementationClass = "kovp.trainhard.convention.AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "trainhard.kotlin.library"
            implementationClass = "kovp.trainhard.convention.KotlinLibraryConventionPlugin"
        }
    }
}
