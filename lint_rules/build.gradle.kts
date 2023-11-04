import java.util.Properties

plugins {
    id("kotlin")
}

val props = Properties().apply {
    file("../build-logic/build.properties").inputStream().use(this::load)
}

val javaVersion = props["javaVersion"].toString()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
