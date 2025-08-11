import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinx.serialization)
    id("app.cash.sqldelight") version "2.1.0"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        configurations.all {
            exclude(group = "org.jetbrains.compose.material", module = "material")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
//            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
            implementation(libs.kotlinx.serialization.json)
            implementation("io.coil-kt.coil3:coil-compose:3.2.0")
            implementation("io.github.ismai117:kottie:2.0.1")
            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")
            implementation("org.jetbrains.compose.material3:material3:1.9.0-alpha04")
//            implementation("org.slf4j:slf4j-simple:2.0.9")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "simple.compose.digfinder.MainKt"

        nativeDistributions {
//            jvmArgs += listOf("--add-modules", "java.sql")
            includeAllModules = true
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//            packageName = "simple.compose.digfinder"
            packageName = "DuplicateImageFinder"
            packageVersion = "1.0.0"
            //设置不开启混淆才能打release包
            buildTypes {
                release {
                    proguard {
                        version = "7.7.0"
                        isEnabled = false
                        configurationFiles.from("proguard-rules.pro")
                    }
                }
            }
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("database")
        }
    }
}
