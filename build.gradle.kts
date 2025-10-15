plugins {
    kotlin("jvm") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "2.0.0" apply false
    id("org.springframework.boot") version "3.3.3" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects {
    group = "com.mfe.family"
    version = "1.0.0"
}

subprojects {
    repositories {
        mavenCentral()
    }
}
