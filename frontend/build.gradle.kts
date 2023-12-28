import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform") version "1.9.22"
    // kotlin("multiplatform") version "2.0.0-Beta1"
}

group = "dev.vanutp.jb_internship_webide"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    wasmJs {
        binaries.executable()
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    // Uncomment and configure this if you want to open a browser different from the system default
                    // open = mapOf(
                    //     "app" to mapOf(
                    //         "name" to "google chrome"
                    //     )
                    // )
                    open = false

                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.rootDir.path)
                    }
                }
            }

            // Uncomment the next line to apply Binaryen and get optimized wasm binaries
            // applyBinaryen()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(npm("@unocss/reset", "0.58.2"))
                implementation(npm("monaco-editor", "0.45.0"))
                implementation(npm("monaco-editor-webpack-plugin", "7.1.0"))
                implementation(npm("file-loader", "6.2.0"))
                implementation(npm("style-loader", "3.3.3"))
                implementation(npm("css-loader", "6.8.1"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val wasmJsMain by getting
        val wasmJsTest by getting
    }
}
