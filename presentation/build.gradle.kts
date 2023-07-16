

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.guardsquare.appsweep") version "1.4.1"
    //  id("org.jlleitschuh.gradle.ktlint") version "11.4.2"
    kotlin("kapt")
}

android {
    namespace = "me.androidbox.presentation"
    compileSdk = 33

    defaultConfig {
        applicationId = "me.androidbox.presentation"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

/* configure kapt to correct error types */
kapt {
    correctErrorTypes = true
}

/*
 * This allows the Hilt annotation processors to be isolating so they are only invoked when necessary.
 * This reduces incremental compilation times by reducing how often an incremental change causes a rebuild of the Dagger components. */
hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(project(":component"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.ui)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.retrofit)
    implementation(libs.core.splashscreen)
    implementation(libs.coil.compose)

    /* TODO Adds these to settings */
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:_")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:_")
    implementation("com.maxkeppeler.sheets-compose-dialogs:date-time:_")
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:_")

    coreLibraryDesugaring(Android.tools.desugarJdkLibs)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.runtime.livedata)
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)
    implementation(libs.accompanist.swiperefresh)
    debugImplementation(tests.ui.tooling)
    debugImplementation(tests.ui.test.manifest)

    testImplementation(tests.junit)
    testImplementation(tests.hilt.android.testing)
    kaptTest(tests.hilt.compiler)

    androidTestImplementation(tests.ext.junit)
    androidTestImplementation(tests.espresso.core)
    androidTestImplementation(tests.ui.test.junit4)
    androidTestImplementation(tests.hilt.android.testing)
    kaptAndroidTest(tests.hilt.compiler)
}