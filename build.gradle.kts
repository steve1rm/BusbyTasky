buildscript {

    dependencies {
        classpath(Google.dagger.hilt.android.gradlePlugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("com.google.dagger.hilt.android") apply false
}