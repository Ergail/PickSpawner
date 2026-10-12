plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":nms:interfaces"))
    compileOnly("org.spigotmc:spigot-api:26.2-R0.1-SNAPSHOT")
    compileOnly(files("../../tmp/26.2/spigot-26.2-R0.1-SNAPSHOT.jar"))
}