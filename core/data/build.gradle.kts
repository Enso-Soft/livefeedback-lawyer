plugins {
    alias(libs.plugins.lafi.android.library)
    alias(libs.plugins.lafi.hilt)
}

android {
    namespace = "com.lafi.lawyer.core.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}