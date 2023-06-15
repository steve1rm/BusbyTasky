plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.coroutines)

    testImplementation(tests.junit)
}