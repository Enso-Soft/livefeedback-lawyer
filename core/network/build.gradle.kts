plugins {
    alias(libs.plugins.lafi.android.library)
    alias(libs.plugins.lafi.hilt)

    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.lafi.lawyer.core.network"
}

dependencies {
    implementation(projects.core.data)
    api(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}