import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType(KotlinCompile::class).all {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide.sdk.core.common"
}

dependencies {
    implementation(project(":common"))
}