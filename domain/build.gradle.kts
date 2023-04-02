plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.coroutines)
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")

    testImplementation(tests.junit)
}