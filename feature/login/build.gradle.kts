plugins {
    alias(libs.plugins.lafi.android.feature)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.lafi.lawyer.feature.login"

    defaultConfig {
        manifestPlaceholders.apply {
            put("KAKAO_OAUTH_KEY", project.properties["KAKAO_OAUTH_KEY"]?.toString() ?: "")
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designSystem)
    implementation(projects.feature.signup)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.v2.user)
}