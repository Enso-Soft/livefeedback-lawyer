plugins {
    alias(libs.plugins.lafi.android.library)
    alias(libs.plugins.lafi.hilt)

    id("kotlinx-serialization")
}

android {
    namespace = "com.lafi.lawyer.core.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.serialization.json)
}