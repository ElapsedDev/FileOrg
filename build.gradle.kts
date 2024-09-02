plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

repositories {
    mavenCentral()
}


dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

group = "me.elapsed.fileorg"
version = "1.0-SNAPSHOT"
description = "FileOrg"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks {


    jar {
        manifest {
            attributes["Main-Class"] = "me.elapsed.fileorg.FileOrg"
        }
    }

    shadowJar {
        archiveFileName.set("FileOrg.jar")
        destinationDirectory.set(file("out"))

    }

}