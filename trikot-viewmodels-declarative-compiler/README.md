# Trikot.viewmodels.declarative.compiler

The Trikot ViewModels Declarative Compiler is a Gradle plugin that generates the required boilerplate code needed when using Trikot ViewModels Declarative, for both Streams and Flow versions.

## Plugin Usage

### Published Property

When a field in a ViewModel interface needs to be _published_, simply annotate it with the `@Published` annotation.

```kotlin
interface ExampleViewModel : VMDViewModel {

    @Published
    val publishedField: String

    val nonPublishedField: String
}
```

When the plugin detects the annotation on an interface, it will automatically generate an abstract base class that your implementation can extend. This class will contain:

- A constructor that requires the initial values of the published fields
- The published property delegate
- The overridden `var` based on the delegate
- The method to update property mapping
- Convenient methods to bind each field individually, or all of them in a single method
- Convenient methods to get the `Publisher`/`Flow` of a field

### Published Subclass

By default, the generated base class will extend the standard ViewModel base class, `VMDViewModelImpl`.
However, if your View Model implementation needs it to extend something else, use the @PublishedSubClass annotation on your implementation.

```kotlin
@PublishedSubClass(superClass = VMDLifecycleViewModelImpl::class)
class ExampleViewModelImpl(
    coroutineScope: CoroutineScope
) : BaseExampleViewModelImpl(
    publishedFieldInitialValue = "initialValue",
    coroutineScope = CoroutineScope
)
```

In this case, `BaseExampleViewModelImpl` will extend `VMDLifecycleViewModelImpl` instead of `VMDViewModelImpl`.

## Plugin installation

The compiler has 2 main components:

- A Kotlin Multiplatform library containing the annotations, needed as a dependency of your kotlin multiplatform code.
- A KSP plugin that handles annotation processing and code generation, only needed at compile time in your project.

### Trikot ViewModels Annotations

Add a dependency to `com.mirego.trikot:viewmodels-annotations:[trikot version]` in your commonMain source set:

```kotlin
val commonMain by getting {
    dependencies {
        api("com.mirego.trikot:viewmodels-annotations:[trikot version]")
    }
}
```

And in your native exports:

```kotlin
framework {
    export("com.mirego.trikot:viewmodels-annotations:[trikot version]")
}
```

### Trikot ViewModels Compiler

The compiler uses KSP (Kotlin Symbol Processing). If you don't have it setup in your project (for another 3rd party plugin), add it in the `plugins` section of your common module gradle script:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}
```

The compiler is not a multi platform project, mainly because it's only needed in the common Koltin code (no need for target-specific code).

In order to add the compiler in your build pipeline, add the following dependency (at the root of your gradle script file).

```kotlin
dependencies {
    add("kspCommonMainMetadata", "com.mirego.trikot:viewmodels-declarative-compiler-[VMD flavor]:[trikot version]")
}
```

Where `VMD flavor` is either `streams` or `flow`, depending on which Trikot Viewmodels Declarative library you are using.

Then, add the source directory to your common source set:

```kotlin
val commonMain by getting {
    dependencies {
        api("com.mirego.trikot:viewmodels-annotations:[trikot version]")
    }
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}
```

This will ensure that both Gradle and your IDE sees the generated code as actual source.

Finally, add the following snippet at the root of your Gradle script to hook the code generation task to any Kotlin compilation task:

```kotlin
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
```
