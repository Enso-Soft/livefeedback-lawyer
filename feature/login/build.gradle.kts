plugins {
    alias(libs.plugins.lafi.android.library)
}

android {
    namespace = "com.enso.login"
}

dependencies {
    implementation(projects.core.designSystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}