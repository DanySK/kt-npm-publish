import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js") version "1.4.10"
//    id("org.jetbrains.kotlin.js") version "1.4.10"
    id("io.github.gciatto.kt-npm-publish")
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin {
    js {
        nodejs {
        }
        binaries.executable()
    }
}

npmPublishing {
    defaultValuesFrom(rootProject)
//    nodeRoot.set(rootProject.tasks.withType<NodeJsSetupTask>().asSequence().map { it.destination }.first())
//    token.set("tokenHere")
//    packageJson.set(tasks.getByName<KotlinPackageJsonTask>("jsPackageJson").packageJson)
//    nodeSetupTask.set(rootProject.tasks.getByName("kotlinNodeJsSetup").path)
//    jsCompileTask.set("jsMainClasses")
//    jsSourcesDir.set(tasks.withType<Kotlin2JsCompile>().asSequence()
//            .filter { "Test" !in it.name }
//            .map { it.outputFile.parentFile }
//            .first())
//    if (nodeRoot.orNull == null) {
//        println("no nodeRoot")
//    }
//    if (packageJson.orNull == null) {
//        println("no packageJson")
//    }
//    if (nodeSetupTask.orNull == null) {
//        println("no nodeSetupTask")
//    }
//    if (jsCompileTask.orNull == null) {
//        println("no jsCompileTask")
//    }
//    if (jsSourcesDir.orNull == null) {
//        println("no jsSourcesDir")
//    }
    liftPackageJson {
        version = "2.3.4"
    }
}