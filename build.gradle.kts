import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath("app.cash.paparazzi:paparazzi-gradle-plugin:1.3.0")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version("8.0.2") apply false
    id("com.android.library") version("8.0.2") apply false
    id("org.jetbrains.kotlin.android") version("1.8.10") apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("com.google.dagger.hilt.android") apply false
    id("org.jlleitschuh.gradle.ktlint") version("11.4.2") apply false
}


subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        this.android.value(true)
        this.ignoreFailures.value(true)
        this.disabledRules.set(listOf(
            "final-newline",
            "parameter-list-wrapping",
            "no-empty-first-line-in-method-block",
            "max-line-length",
            "wrapping",
            "argument-list-wrapping",
            "curly-spacing",
            "no-wildcard-imports",
            "package-name"
        ))

        this.filter {
            exclude {
                it.file.path.contains("test")
            }
            exclude { element ->
                element.file.path.contains("androidTest")
            }
        }

        reporters {
            this.reporter(ReporterType.CHECKSTYLE)
            this.reporter(ReporterType.HTML)
            this.reporter(ReporterType.JSON)
        }
    }
}
