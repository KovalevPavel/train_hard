package me.kovp.trainhard.convention.utils

import me.kovp.trainhard.convention.consts.Config
import me.kovp.trainhard.convention.consts.Plugins.java
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin(extensions: JavaPluginExtension) {
    extensions.apply {
        toolchain.languageVersion.set(JavaLanguageVersion.of(Config.javaVersion.toString()))
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
        }
    }
}
