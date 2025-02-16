import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.enso.convention.configureFlavors
import com.enso.convention.configureGradleManagedDevices
import com.enso.convention.configureKotlinAndroid
import com.enso.convention.configurePrintApksTask
import com.enso.convention.disableUnnecessaryAndroidTests
import com.enso.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                buildFeatures {
                    viewBinding = true
                    buildConfig = true
                }

                testOptions.animationsDisabled = true
                configureFlavors(this)
                configureGradleManagedDevices(this)
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }

            dependencies {
                add("androidTestImplementation", libs.findLibrary("kotlin.test").get())
                add("testImplementation", libs.findLibrary("kotlin.test").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}