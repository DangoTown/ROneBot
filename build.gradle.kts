import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    id("maven-publish")
}

val libVersion: String by project

subprojects {
    group = "cn.rtast"
    version = libVersion

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        apply(plugin = "maven-publish")
    }

    val sourceJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    artifacts {
        archives(sourceJar)
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        api(kotlin("stdlib"))
        api("com.google.code.gson:gson:2.11.0")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    }

    tasks.jar {
        from("LICENSE") {
            rename { "ROneBot-LICENSE-Apache2.0" }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifact(sourceJar)
                artifactId = project.name
                version = libVersion
            }
        }

        repositories {
            maven {
                url = uri("https://repo.rtast.cn/api/v4/projects/33/packages/maven")
                credentials {
                    username = "RTAkland"
                    password = System.getenv("TOKEN")
                }
            }
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    tasks.compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    base {
        archivesName = rootProject.name + "-${project.name}"
    }
}