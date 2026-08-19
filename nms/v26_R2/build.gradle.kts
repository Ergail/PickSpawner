plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":nms:interfaces"))
    compileOnly("org.spigotmc:spigot-api:26.2-R0.1-SNAPSHOT")
    // Full server jar (with NMS classes), only available via mavenLocal() - see root build.gradle.kts.
    // Install it locally with: java -jar BuildTools.jar --rev 26.2 --compile spigot
    compileOnly("org.spigotmc:spigot:26.2-R0.1-SNAPSHOT")
}
