plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("io.strikt:strikt-jvm:0.34.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
    test {
        kotlin.srcDir("test")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
    test {
        useJUnitPlatform()
    }
}

