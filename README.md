# Publishing Kotlin Multi-Platform Projects to NPM

A Gradle plugin letting developers upload Kotlin-JS or -MPP projects on NPM.
It requires a Gradle project including one of the following plugins:
- `org.jetbrains.kotlin.js`
- `org.jetbrains.kotlin.multiplatform`

In both cases, the plugin assumes a Node Js target has been added to your project, explicitly, via the syntax:
```kotlin
kotlin {
    js {
        nodeJs {
            // ...
        }
    }
}
```

The plugin does _not_ apply any of the aforementioned `org.jetbrains.kotlin.*` plugins behind the scenes.
Thus, it is important to apply them accordingly.

The basic configuration of the plugin is as follows:
```kotlin
npmPublishing {
    token.set("<NPM authentication token here>")
}
```
This adds a numbers of tasks to your project.
The most relevant one is `:npmPublish` which essentially packs and uploads the JS project generated by the Kotlin plugin 
out of the Kotlin code.
This task also includes any file in the root project's directory whose name matches `README*`, `CONTRIB*`, or `LICENSE*`.

<!-- It is important to call `defaultValuesFrom(project)` AFTER setting up the `nodeJs` target within the `kotlin` section.
Otherwise, the procedure configuring the plugin automatically won't work. -->

Users may override a number of properties within the `npmPublishing`, like for instance:
```kotlin
npmPublishing {
    token.set("<NPM authentication token here>")

    registry.set("<custom NPM registry here>") // defaults to registry.npmjs.org
}
```

The plugin may also _lift_ the `package.json` file generated by Kotlin, as well as any `.js` file, in a programmatic way.
The lifting if performed before publication and it is aimed at customising the data published on the NPM registry.
For instance, one may override any information generated by the Kotlin compiler, change the name of the generated project,
and so on.

Lifting is based on the `liftPackageJson` and `liftJsSources` methods which are available within the `npmPublishing` section.
In particular, `liftPackageJson` accepts a lambda expression as input.
Within that lambda expression, `this` refers to an object representing the current value of the generated `package.json` file.
The user may update any field, and each modification will be reified into the `package.json` file.

For instance, the following snippet adds a number a developer to the generated `package.json`, other than setting a few common metadata:
```kotlin
npmPublishing {
    token.set("<NPM authentication token here>")

    liftPackageJson {
        people = mutableListOf(People("Developer Name", "Developer Email", "Developer Homepage"))
        homepage = "Project homepage"
        bugs = Bugs("Issues Web Page", "Issues Email Address")
        license = "Apache-2.0"
    }
}
```

If you want to publish your project (say `test-js`) under the organization named `organization`, you should lift 
both the generated `package.json` and the generated `.js` files as follows:
```kotlin
npmPublishing {
    token.set("<NPM authentication token here>")

    liftPackageJson {
        name = "@organization/$name"
        dependencies = dependencies?.mapKeys { (key, _) ->
           if (name in key) "@organization/$key" else key
        }
    }

    liftJsSources { file, i, line ->
        line.replace("'test-mpp", "'@organization/test-mpp")
            .replace("\"test-mpp", "\"@organization/test-mpp")
    }
}
```