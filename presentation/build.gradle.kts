plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
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

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
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
    implementation("io.coil-kt:coil-compose:2.1.0")

    /* TODO Adds these to settings */
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.1.1")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.1.1")
    implementation("com.maxkeppeler.sheets-compose-dialogs:date-time:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.3")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.work:work-runtime-ktx:2.8.0")
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")
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