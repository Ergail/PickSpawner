plugins {
    kotlin("jvm") version Versions.kotlinJvm
    id("com.gradleup.shadow") version Versions.shadowJar
}

allprojects {
    group = "fr.fabienhebuterne"
    version = System.getProperty("tagVersion") ?: "SNAPSHOT"

    apply(plugin = "kotlin")

    repositories {
        // mavenLocal is needed for org.spigotmc:spigot:26.2-R0.1-SNAPSHOT (full NMS jar), which is
        // not published anywhere public and must be produced locally with Spigot BuildTools
        // (`java -jar BuildTools.jar --rev 26.2`, requires a Java 25/26 JDK) before building nms:v26_R2.
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net/")
    }

    defaultDependencies()
    testDependencies()

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
            vendor.set(JvmVendorSpec.ADOPTIUM)
        }
    }

}

// This is workaround to fix build issue with jackson 2.17.x and shadowJar 8.1.x
buildscript {
    configurations {
        classpath {
            resolutionStrategy {
                //in order to handle jackson's higher release version in shadow, this needs to be upgraded to latest.
                force("org.ow2.asm:asm:9.6")
                force("org.ow2.asm:asm-commons:9.6")
            }
        }
    }
}