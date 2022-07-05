plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    java
}

group = "nyc.cleo"
version = "1.0-SNAPSHOT"
val kotlinJvmTarget = JavaVersion.toVersion(17)

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "$kotlinJvmTarget"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "$kotlinJvmTarget"
        }
    }
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
    test {
        useJUnitPlatform()
    }
    shadowJar {
        minimize()
    }

    named<DefaultTask>("build") {
        dependsOn("shadowJar")
    }
//    jar {
//        val dependencies = configurations.runtimeClasspath.get().map(::zipTree)
//        from(dependencies)
//        // We may omit them without any troubles or errors because we never use the Kotlin Reflect library in this project.
//        exclude(
//            "**/*.kotlin_metadata",
//            "**/*.kotlin_module",
//            "**/*.kotlin_builtins",
//            "**/*.pro",
//            "**/*.version",
//            "**/*.bin",
//            "META-INF/maven/**",
//            "META-INF/versions/**"
//        )
//        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//    }
}