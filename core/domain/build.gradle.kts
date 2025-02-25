plugins {
    alias(libs.plugins.lafi.jvm.library)
    alias(libs.plugins.lafi.hilt)
}

dependencies {
    api(projects.core.model)
}
