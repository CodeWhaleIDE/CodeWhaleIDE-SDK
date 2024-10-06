plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide.sdk.common"
}

dependencies {
    api(libs.bundles.kotlin)
    api(libs.bundles.androidx)
    api(libs.bundles.compose)

    api("io.github.stoyan-vuchev:squircle-shape:2.0.0")
    api("me.saket.cascade:cascade-compose:2.3.0")
    api("io.morfly.compose:advanced-bottomsheet-material3:0.1.0")
}