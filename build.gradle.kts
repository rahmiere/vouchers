plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "org.killjoy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.broccol.ai/releases/")


}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.20.4-R0.1-SNAPSHOT")

    implementation("com.google.inject", "guice", "7.0.0")
    implementation("com.google.inject.extensions", "guice-assistedinject", "7.0.0")

    implementation("org.spongepowered", "configurate-gson", "4.1.2")

    implementation("cloud.commandframework", "cloud-paper", "1.8.4")
    implementation("org.incendo.interfaces", "interfaces-paper" ,"1.0.0-SNAPSHOT")

    implementation("de.tr7zw", "item-nbt-api", "2.12.2")

    implementation("broccolai.corn", "corn-minecraft-paper", "3.2.0")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    processResources {
        expand("version" to project.version)
    }

    shadowJar {
        archiveBaseName.set("Vouchers")
        archiveClassifier.set("")

        val libsPackage = "${project.group}.${project.name}.libs"
        relocate("com.google.inject", "$libsPackage.guice")
        relocate("cloud.commandframework", "$libsPackage.cloud")
        relocate("org.spongepowered.configurate", "$libsPackage.configurate")
        relocate("de.tr7zw.changeme.nbtapi", "$libsPackage.nbt")
    }

    runServer {
        minecraftVersion("1.20.4")
    }
}