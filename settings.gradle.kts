rootProject.name = "BusbyTasky"

include(
    ":presentation",
    ":domain",
    ":data",
    ":component"
)

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.51.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        createLibs()
        createTests()
    }
}

fun MutableVersionCatalogContainer.createLibs() {
    this.create("libs") {
        version("version.androidx.core", "_")
        version("version.androidx.lifecycle", "_")
        version("version.androidx.activity", "_")
        version("version.androidx.compose.material3", "_")
        version("version.androidx.compose.ui", "_")
        version("version.androidx.appcompat", "_")
        version("version.google.dagger", "_")
        version("version.androidx.room", "_")
        version("version.kotlinx.coroutines", "_")
        version("version.moshi", "_")
        version("version.androidx.hilt-navigation-compose", "_")
        version("version.retrofit2", "_")
        version("version.okhttp3", "_")

        /* UI */
        library("core-ktx", "androidx.core", "core-ktx").versionRef("version.androidx.core")
        library("lifecycle-runtime-ktx","androidx.lifecycle", "lifecycle-runtime-ktx").version("version.androidx.lifecycle")
        library("activity-compose", "androidx.activity", "activity-compose").version("version.androidx.activity")
        library("material", "androidx.compose.material3", "material3").versionRef("version.androidx.compose.material3")
        library("ui","androidx.compose.ui", "ui").version("version.androidx.compose.ui")
        library("ui-tooling-preview","androidx.compose.ui", "ui-tooling-preview").version("version.androidx.compose.ui")
        library("appcompat","androidx.appcompat", "appcompat").versionRef("version.androidx.appcompat")

        /* Coroutines */
        library("coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("version.kotlinx.coroutines")

        /* Dagger */
        library("hilt-android","com.google.dagger", "hilt-android").versionRef("version.google.dagger")
        library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("version.google.dagger")
        library("hilt-navigation-compose","androidx.hilt", "hilt-navigation-compose").versionRef("version.androidx.hilt-navigation-compose")

        /* Room */
        library("room-runtime", "androidx.room", "room-runtime").versionRef("version.androidx.room")
        library("room-compiler", "androidx.room", "room-compiler").versionRef("version.androidx.room")
        library("room-ktx", "androidx.room", "room-ktx").versionRef("version.androidx.room")

        /* Retrofit */
        library("moshi-kotlin","com.squareup.moshi", "moshi-kotlin").versionRef("version.moshi")
        library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("version.retrofit2")
        library("converter-moshi", "com.squareup.retrofit2", "converter-moshi").versionRef("version.retrofit2")
        library("logging-interceptor","com.squareup.okhttp3", "logging-interceptor").versionRef("version.okhttp3")

        //  bundle("retrofit", listOf("moshi-kotlin", "retrofit", "logging-interceptor", "converter-model"))
        bundle("compose", listOf("activity-compose", "material", "ui", "ui-tooling-preview"))
    }
}

fun MutableVersionCatalogContainer.createTests() {
    this.create("tests") {
        version("version.junit.junit", "_")
        version("version.androidx.test.ext.junit", "_")
        version("version.androidx.test.espresso", "_")
        version("version.androidx.compose.ui", "_")
        version("version.google.dagger", "_")
        version("version.com.google.truth", "_")

        library("junit", "junit", "junit").versionRef("version.junit.junit")
        library("ext-junit","androidx.test.ext", "junit").versionRef("version.androidx.test.ext.junit")
        library("espresso-core","androidx.test.espresso", "espresso-core").version("version.androidx.test.espresso")
        library("ui-test-junit4","androidx.compose.ui","ui-test-junit4").versionRef("version.androidx.compose.ui")
        library("ui-tooling","androidx.compose.ui", "ui-tooling").versionRef("version.androidx.compose.ui")
        library("ui-test-manifest","androidx.compose.ui", "ui-test-manifest").versionRef("version.androidx.compose.ui")
        library("hilt-android-testing", "com.google.dagger","hilt-android-testing").versionRef("version.google.dagger")
        library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("version.google.dagger")
        library("truth", "com.google.truth", "truth").versionRef("version.com.google.truth")

        bundle("compose", listOf("ui-test-junit4", "ui-tooling", "ui-test-manifest"))
    }
}
