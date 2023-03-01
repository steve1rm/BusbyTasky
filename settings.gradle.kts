
rootProject.name = "BusbyTasky"
include(":presentation", ":domain", ":data")

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
        version("version.androidx.compose.material", "_")
        version("version.androidx.compose.ui", "_")
        version("version.androidx.appcompat", "_")
        version("version.google.dagger", "_")

        library("core-ktx", "androidx.core", "core-ktx").versionRef("version.androidx.core")
        library("lifecycle-runtime-ktx","androidx.lifecycle", "lifecycle-runtime-ktx").version("version.androidx.lifecycle")
        library("activity-compose", "androidx.activity", "activity-compose").version("version.androidx.activity")
        library("material", "androidx.compose.material", "material").versionRef("version.androidx.compose.material")
        library("ui","androidx.compose.ui", "ui").version("version.androidx.compose.ui")
        library("ui-tooling-preview","androidx.compose.ui", "ui-tooling-preview").version("version.androidx.compose.ui")
        library("appcompat","androidx.appcompat", "appcompat").versionRef("version.androidx.appcompat")

        library("hilt-android","com.google.dagger", "hilt-android").versionRef("version.google.dagger")
        library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("version.google.dagger")

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

        library("junit", "junit", "junit").versionRef("version.junit.junit")
        library("ext-junit","androidx.test.ext", "junit").versionRef("version.androidx.test.ext.junit")
        library("espresso-core","androidx.test.espresso", "espresso-core").version("version.androidx.test.espresso")
        library("ui-test-junit4","androidx.compose.ui","ui-test-junit4").versionRef("version.androidx.compose.ui")
        library("ui-tooling","androidx.compose.ui", "ui-tooling").versionRef("version.androidx.compose.ui")
        library("ui-test-manifest","androidx.compose.ui", "ui-test-manifest").versionRef("version.androidx.compose.ui")
        library("hilt-android-testing", "com.google.dagger","hilt-android-testing").versionRef("version.google.dagger")
        library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("version.google.dagger")

        bundle("compose", listOf("ui-test-junit4", "ui-tooling", "ui-test-manifest"))
    }
}
