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

}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.20.4-R0.1-SNAPSHOT")

    implementation("com.google.inject", "guice", "7.0.0")
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
    }

    runServer {
        minecraftVersion("1.20.4")
    }
}