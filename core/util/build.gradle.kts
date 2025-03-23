plugins {
    alias(libs.plugins.lafi.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lafi.lawyer.core.util"
}

dependencies {

    //implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}