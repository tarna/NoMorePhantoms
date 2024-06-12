plugins {
    kotlin("jvm") version "1.9.23"
    id("io.github.goooler.shadow") version "8.1.7"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "dev.tarna"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    implementation("com.github.DebitCardz:mc-chestui-plus:1.5.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

tasks {
    shadowJar {
        relocate("org.bstats", "dev.tarna.nomorephantoms.bstats")
    }

    runServer {
        minecraftVersion("1.20.6")
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}