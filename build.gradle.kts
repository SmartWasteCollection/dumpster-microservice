plugins {
    kotlin("jvm") version "1.7.0"
    id("org.springframework.boot") version "2.7.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.3.0"
    jacoco
    application
}

group = "swc"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
    testImplementation("io.kotest:kotest-assertions-core:5.3.2")
    testImplementation("io.kotest:kotest-property:5.3.2")
    implementation("com.azure:azure-digitaltwins-core:1.2.4")
    implementation("com.azure:azure-identity:1.5.3")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
}

application {
    mainClass.set("swc.DumpsterMicroserviceKt")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
