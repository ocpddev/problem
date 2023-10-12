import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kapt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management)
    `maven-publish`
    signing
}

group = "dev.ocpd.spring"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.named<Jar>("javadocJar") {
    from(tasks.named("dokkaJavadoc"))
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports { mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) }
}

extra["kotlin.version"] = getKotlinPluginVersion()

dependencies {
    // Dog food
    implementation(libs.slf4k)

    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.springframework.boot:spring-boot-starter-security")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("problem")
                description.set("Spring Boot Problem support")
                url.set("https://github.com/ocpddev/problem")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/ocpddev/problem")
                }
                developers {
                    developer {
                        id.set("sola")
                        name.set("Sola")
                        email.set("sola@ocpd.dev")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releaseUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            url = if (version.toString().endsWith("-SNAPSHOT")) snapshotUrl else releaseUrl

            credentials {
                username = project.findSecret("ossrh.username", "OSSRH_USERNAME")
                password = project.findSecret("ossrh.password", "OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    val key = findSecret("ocpd.sign.key", "OCPD_SIGN_KEY")
    if (key != null) {
        val keyId = findSecret("ocpd.sign.key.id", "OCPD_SIGN_KEY_ID")
        val passphrase = findSecret("ocpd.sign.passphrase", "OCPD_SIGN_PASSPHRASE") ?: ""
        useInMemoryPgpKeys(keyId, key, passphrase)
    }
    sign(publishing.publications["maven"])
}

fun Project.findSecret(key: String, env: String): String? =
    project.findProperty(key) as? String ?: System.getenv(env)
