plugins {
    id("trainhard.kotlin.library")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.collections.immutable)
}
