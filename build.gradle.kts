plugins {
    kotlin("jvm") version "1.7.20"
    id("org.springframework.boot") version "2.7.5"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
    jacoco
    application
}

group = "swc"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest:kotest-property:5.5.4")
    implementation("com.azure:azure-digitaltwins-core:1.3.3")
    implementation("com.azure:azure-identity:1.5.3")
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
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
