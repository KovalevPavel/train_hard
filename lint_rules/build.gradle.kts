import java.util.Properties

plugins {
    id("kotlin")
}

val props = Properties().apply {
    file("../builder/plugins/build.properties").inputStream().use(this::load)
}

val javaVersion = props["javaVersion"].toString()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
