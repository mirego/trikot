# Trikot Swift Extensions Plugin

Gradle plugin that syncs Trikot's iOS Swift extensions into consumer projects. This replaces CocoaPods subspecs for distributing Swift extensions, enabling SPM-based projects to use Trikot without CocoaPods.

## How it works

Each Trikot module can ship Swift extensions in a `swift-extensions/` directory (e.g. `trikot-viewmodels/swift-extensions/`). The plugin packages all of these into its JAR at build time, auto-discovering modules and files from the filesystem — no manual manifest is needed.

At sync time, the plugin:

1. Copies the requested Swift files into the consumer's output directory
2. Replaces `import TRIKOT_FRAMEWORK_NAME` with the consumer's configured framework name
3. Removes `import Trikot` lines (no longer needed outside CocoaPods)

Consumer apps are responsible for having the required third-party dependencies (e.g. Kingfisher, Reachability) available in their project for whichever modules they use.

## Installation

Add the plugin to your project's `build.gradle.kts`:

```kotlin
plugins {
    id("com.mirego.trikot.swift-extensions") version "$trikot_version"
}
```

## Configuration

```kotlin
trikotSwiftExtensions {
    frameworkName.set("Shared")                // Required — your KMP framework name
    outputDir.set(file("ios/TrikotExtensions")) // Required — where Swift files are written
    modules.set(listOf(                         // Optional — defaults to all available modules
        "streams",
        "viewmodels",
        "viewmodels-kingfisher",
        "http",
        "kword",
    ))
}
```

### Properties

| Property        | Type           | Default       | Description                                          |
| --------------- | -------------- | ------------- | ---------------------------------------------------- |
| `frameworkName` | `String`       | —             | The KMP framework name used in `import` statements.  |
| `outputDir`     | `Directory`    | —             | Directory where Swift extension files are written.   |
| `modules`       | `List<String>` | all available | Trikot modules to sync. Empty list syncs everything. |

## Usage

Run the sync task:

```bash
./gradlew syncTrikotSwiftExtensions
```

This will populate the output directory with one subdirectory per module:

```
ios/TrikotExtensions/
  http/
    TrikotConnectivityService.swift
    TrikotHttpRequest.swift
    ...
  viewmodels/
    UIButtonExtensions.swift
    UIViewExtensions.swift
    ...
  viewmodels-kingfisher/
    KFImageViewModelHandler.swift
  ...
```

## Available modules

Modules are auto-discovered from `trikot-*/swift-extensions/` directories. Currently available:

| Module                  | Dependencies      | Description                   |
| ----------------------- | ----------------- | ----------------------------- |
| `streams`               | —                 | Publisher extensions          |
| `streams-combine`       | —                 | Combine framework interop     |
| `viewmodels`            | —                 | UIKit view model bindings     |
| `viewmodels-kingfisher` | Kingfisher        | Image loading with Kingfisher |
| `http`                  | Reachability      | HTTP client and connectivity  |
| `kword`                 | —                 | i18n string extensions        |
| `bluetooth`             | —                 | CoreBluetooth extensions      |
| `analytics-firebase`    | FirebaseAnalytics | Firebase analytics service    |
| `analytics-mixpanel`    | Mixpanel          | Mixpanel analytics service    |

## Adding Swift extensions to a Trikot module

To add Swift extensions to an existing or new Trikot module, place `.swift` files in a `swift-extensions/` directory under the module:

```
trikot-mymodule/
  swift-extensions/
    MyExtension.swift
```

Sub-modules are supported via subdirectories:

```
trikot-mymodule/
  swift-extensions/
    MyExtension.swift          # module key: "mymodule"
    subfeature/
      SubExtension.swift       # module key: "mymodule-subfeature"
```

Use `import TRIKOT_FRAMEWORK_NAME` in your Swift files — it will be replaced with the consumer's framework name at sync time.
