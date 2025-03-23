plugins {
    alias(libs.plugins.lafi.android.feature)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lafi.lawyer.feature.signup"

    defaultConfig {
        manifestPlaceholders.apply {
            put("KAKAO_OAUTH_KEY", project.properties["KAKAO_OAUTH_KEY"]?.toString() ?: "")
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designSystem)
    implementation(projects.core.util)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.v2.user)
}