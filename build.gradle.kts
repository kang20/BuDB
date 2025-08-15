plugins {
    java
    id("org.springframework.boot") version "3.5.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("org.ec4j.editorconfig") version "0.1.0"
    id("checkstyle")
    id("com.github.spotbugs") version "6.2.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    group = "io"
    version = "0.0.1-SNAPSHOT"
    description = "BuDB"

    repositories {
        mavenCentral()
    }
}



subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    apply(plugin = "checkstyle")

    configure<CheckstyleExtension> {
        toolVersion = "8.42"
        configFile = rootProject.file("rule-config/naver-checkstyle-rules.xml")
        configProperties = mapOf(
            "suppressionFile" to rootProject.file("rule-config/naver-checkstyle-suppressions.xml")
        )
        maxWarnings = 0
        isIgnoreFailures = false
    }

    // main 소스만 대상으로 지정
    tasks.named<Checkstyle>("checkstyleMain") {
        setSource(fileTree("src/main/java"))
    }
    // 필요시 test는 비활성화 (없으면 무시)
    tasks.findByName("checkstyleTest")?.apply { enabled = false }

    tasks.named("check") {
        dependsOn("checkstyleMain")
    }

}

checkstyle {
    toolVersion = "8.42"
    configFile = file("${rootDir}/rule-config/naver-checkstyle-rules.xml")
    configProperties = mapOf(
        "suppressionFile" to "${rootDir}/rule-config/naver-checkstyle-suppressions.xml"
    )
    maxWarnings = 0
    isIgnoreFailures = false
}

spotbugs {
    excludeFilter.set(file("${projectDir}/spotbugs-exclude-filter.xml"))
}