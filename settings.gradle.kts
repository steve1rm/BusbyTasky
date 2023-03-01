
rootProject.name = "BusbyTasky"
include(":presentation", ":domain", ":data")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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
        version("core-ktx", "1.9.0")
        version("lifecycle-runtime-ktx", "2.5.1")
        version("activity-compose", "1.6.1")
        version("material", "1.3.1")
        version("compose", "1.3.3")
        version("appcompat", "1.6.1")

        library("core-ktx", "androidx.core", "core-ktx").versionRef("core-ktx")
        library("lifecycle-runtime-ktx","androidx.lifecycle", "lifecycle-runtime-ktx").version("lifecycle-runtime-ktx")
        library("activity-compose", "androidx.activity", "activity-compose").version("activity-compose")
        library("material", "androidx.compose.material", "material").versionRef("material")
        library("ui","androidx.compose.ui", "ui").version("compose")
        library("ui-tooling-preview","androidx.compose.ui", "ui-tooling-preview").version("compose")
        library("appcompat","androidx.appcompat", "appcompat").versionRef("appcompat")

        bundle("compose", listOf("activity-compose", "material", "ui", "ui-tooling-preview"))
    }
}

fun MutableVersionCatalogContainer.createTests() {
    this.create("tests") {
        version("junit", "4.13.2")
        version("ext-junit", "1.1.5")
        version("espresso-core", "3.5.1")
        version("compose", "1.3.3")

        library("junit", "junit", "junit").versionRef("junit")
        library("ext-junit","androidx.test.ext", "junit").versionRef("ext-junit")
        library("espresso-core","androidx.test.espresso", "espresso-core").version("espresso-core")
        library("ui-test-junit4","androidx.compose.ui","ui-test-junit4").versionRef("compose")
        library("ui-tooling","androidx.compose.ui", "ui-tooling").versionRef("compose")
        library("ui-test-manifest","androidx.compose.ui", "ui-test-manifest").versionRef("compose")

        bundle("compose", listOf("ui-test-junit4", "ui-tooling", "ui-test-manifest"))
    }
}
