plugins {
    kotlin("multiplatform") version "{{KOTLIN_VERSION}}"
    {{SHADOW_JAR}}
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    // 补药移除这里的仓库否则会导致无法下载依赖
    maven("https://repo.maven.rtast.cn/releases")
}

kotlin {
    {{LINUX}}
    {{LINUX_ARM}}
    {{MINGW}}
    {{MACOS}}
    {{MACOS_ARM}}
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
            }
        }
    }
}

{{SHADOW_JAR_CONFIG}}