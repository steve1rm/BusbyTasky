plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "me.androidbox.component"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation("io.coil-kt:coil-compose:2.1.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    debugImplementation(tests.ui.tooling)

    testImplementation(tests.junit)
    androidTestImplementation(tests.ext.junit)
    androidTestImplementation(tests.espresso.core)
    androidTestImplementation(tests.ui.test.junit4)
}