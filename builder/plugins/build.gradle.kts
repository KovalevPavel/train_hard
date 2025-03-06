import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    `kotlin-dsl`
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    toolchain {
        toolchain.languageVersion.set(JavaLanguageVersion.of(projectJavaVersion.toString()))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication_deprecated") {
            id = "trainhard.android.application"
            implementationClass = "kovp.trainhard.plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary_deprecated") {
            id = "trainhard.android.library"
            implementationClass = "kovp.trainhard.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose_deprecated") {
            id = "trainhard.android.compose"
            implementationClass = "kovp.trainhard.plugins.AndroidComposeConventionPlugin"
        }
        register("kotlinLibrary_deprecated") {
            id = "trainhard.kotlin.library"
            implementationClass = "kovp.trainhard.plugins.KotlinLibraryConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "th.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("platformLibrary") {
            id = "th.platform.library"
            implementationClass = "PlatformLibraryConventionPlugin"
        }

        register("compose") {
            id = "th.compose"
            implementationClass = "PlatformComposeConventionPlugin"
        }

        register("application") {
            id = "th.application"
            implementationClass = "ApplicationConventionPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.gradleplugin.compose)
    compileOnly(libs.gradleplugin.android)
    compileOnly(libs.gradleplugin.composeCompiler)
    compileOnly(libs.gradleplugin.kotlin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(libs.androidx.benchmark.baseline.profile.gradle.plugin)
}
