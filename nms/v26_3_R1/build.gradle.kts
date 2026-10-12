plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":nms:interfaces"))
    // spigot-api 26.3 isn't published yet: paper-api exposes the same Bukkit API.
    compileOnly("io.papermc.paper:paper-api:26.3.build.177-stable")
    // Full server jar with NMS classes (Mojang mappings), see install-dependency-in-cache.sh
    compileOnly(files("../../tmp/26.3/paper-26.3.jar"))
}
