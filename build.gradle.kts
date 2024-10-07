import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType(KotlinCompile::class).all {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
}

private fun BaseExtension.configureBaseExtension() {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun Project.configureApplicationExtension() {
    extensions.findByType<AppExtension>()?.run {
        configureBaseExtension()
    }
}

fun Project.configureLibraryExtension() {
    extensions.findByType<LibraryExtension>()?.run {
        configureBaseExtension()
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}

subprojects {
    plugins.withId("com.android.application") { configureApplicationExtension() }
    plugins.withId("com.android.library") { configureLibraryExtension() }
    plugins.withId("org.jetbrains.kotlin.plugin.compose") {
        extensions.findByType<BaseExtension>()?.run {
            buildFeatures.compose = true
        }
    }
}