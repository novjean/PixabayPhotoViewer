// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    val kotlin_version by extra("1.9.0")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.3.2")  // Or your AGP version
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")  // Hilt plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.2")  // Use the latest version


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}