plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "me.androidbox.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        /* Retrieves API from local.properties */
        val properties = org.jetbrains.kotlin.konan.properties.Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "BUSBY_TASKY_API_KEY", "\"${properties.getProperty("BUSBY_TASKY_API_KEY")}\"")
        buildConfigField("String", "BUSBY_TASKY_API", "\"https://tasky.pl-coding.com\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
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
    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    // Coroutines
    implementation(libs.coroutines)

    // Dagger
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room components
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Encrypted shared preferences
    implementation(libs.security.crypto)
    implementation(libs.work.runtime.ktx)
    coreLibraryDesugaring(Android.tools.desugarJdkLibs)
    implementation(libs.hilt.work)
    kapt(libs.hilt.compiler)

    androidTestImplementation(tests.hilt.android.testing)
    kaptTest(tests.hilt.compiler)
    testImplementation(tests.junit)
    testImplementation(tests.truth)
    androidTestImplementation(tests.junit)
    androidTestImplementation(tests.truth)
    androidTestImplementation(tests.ext.junit)
    androidTestImplementation(tests.ui.test.junit4)
}