# Changelog

All notable changes to the library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## Upcoming

### Breaking Changes

- Minimum Kotlin compiler version `2.2.0` must be use in order to consume this library

- `kotlinx-datetime` version `0.7.0` no longer provide the `Instant` nor the `Clock` classes. In order to prevent runtime issues, update the `kotlinx-datetime` dependency to `0.7.0` in your project and use the `kotlin.time` imports (see https://github.com/Kotlin/kotlinx-datetime?tab=readme-ov-file#deprecation-of-instant).

- [kword] Gradle plugin DSL changed. All properties are now lazy.
  `translationFile` was replaced with `translationFiles` to support multiple translation files.
- `enumClassName` was renamed to `targetClassName` and it generates constant for improved performance.
- Gradle task has been renamed from `kwordGenerateEnum` to `kwordGenerate`.

Instead of

```kotlin
  translationFile = file("src/commonMain/resources/translations/translation.en.json")
  enumClassName = "com.mirego.sample.KWordTranslation"
  generatedDir = file("src/commonMain/generated")
```

Do this

```kotlin
  translationFiles.setFrom(file("src/commonMain/resources/translations/translation.en.json"))
  targetClassName.set("com.mirego.sample.KWordTranslation")
  generatedDir.set(file("src/commonMain/generated"))
```

Replace task dependencies from

```kotlin
  dependsOn(tasks.withType<com.mirego.kword.KWordEnumGenerate>())
```

to

```kotlin
  dependsOn(tasks.withType<com.mirego.kword.KWordGenerate>())
```

_Note that you may have to comment this line to sync your project the first time._

- [viewmodels] Picasso was replaced with Coil.
  _To migrate:_
  - Replace `com.squareup.picasso:picasso:2.71828` with `io.coil-kt.coil3:3.1.0` in your Android modules.
  - Optional: replace `jp.wasabeef:picasso-transformations` with `com.github.Commit451.coil-transformations:transformations:3.0.7` in your Android modules for image transformations.

  By default, `ImageViewModelBinder.bind(...)` remains mostly the same as before, except for transformations that must be reimplemented using the interface `coil3.transform.Transformation` and the parameter is now a list called `transformations`. Additionally, a new parameter `imageLoader` was added to allow overriding the default cache. Documentation on how to override Coil's cache has been added to the module's [README.md](./trikot-viewmodels/README.md).

  Instead of

  ```kotlin
  val transformation = jp.wasabeef.picasso.transformations

  ImageViewModelBinder.bind(
    imageView = imageView,
    imageViewModel = imageViewModel,
    lifecycleOwnerWrapper = lifecycleOwnerWrapper,
    transformation = transformation, // Custom transformation that implements [com.squareup.picasso.Transformation].
    placeholderScaleType = placeholderScaleType
  )
  ```

  Do this

  ```kotlin
  ImageViewModelBinder.bind(
    imageView = imageView,
    imageViewModel = imageViewModel,
    lifecycleOwnerWrapper = lifecycleOwnerWrapper,
    transformations = listOf(transformation), // Custom transformation that implements [coil3.transform.Transformation]. Multiple transformations are now accepted.
    placeholderScaleType = placeholderScaleType,
    imageLoader = imageLoader // New optional parameter to use a custom cache
  )
  ```

- [VMD] Coil was upgraded to coil3.
  - While it is not necessary to migrate your coil dependency you will have to change some imports when using VMDImage

```kotlin
      import coil.compose.AsyncImagePainter
```

```kotlin
      import coil3.compose.AsyncImagePainter
```

    _To migrate:_

- Follow [Coil official migration](https://coil-kt.github.io/coil/upgrading_to_coil3/)

- [HTTP] Ktor was upgraded to '3.3.1'
  - No API changes, but having both Ktor 2.x and 3.x in the same project will cause runtime issues. Make sure you upgrade your Ktor dependency to 3.3.1 in your project.
  - See [Ktor 3.0.0 migration guide](https://ktor.io/docs/migrating-3.html#ktor-client) for more details.

### Updates

## 5.4.0

> 2024-08-06

### Breaking Changes

- [VMD] Change parameters order for some Compose components
- [VMD] Change VMDTextField's signature on Android
- [VMD] Change VMDTextStyleProvider::textStyleForResource signature (now Composable)

### Updates

- Kotlin `2.0.0`
- Gradle to `8.8`
- AGP to `8.5.0`
- Ktlint to `11.6.1`
- Ktor to `2.3.12`
- Kotlin Serialization to `1.6.0`
- Kotlin Wrappers Extensions to `1.0.1-pre.628`
- Androidx Lifecycle to `2.6.2`
- ThreeTen Android Backport to `1.4.6`
- Kingfisher to `7.10.1`
- ReachabilitySwift to `5.2`
- AtomicFu to `0.24.0`
- Accompanist to `0.34.0`
- Okio to `3.9.0`
- Jetpack Compose Compiler plugin to `2.0.0`
- Jetpack Compose Runtime to `1.6.8`

## 5.3.0

> 2024-01-09

### Updates

- [VMD] Android tv bindings
- [VMD] VMDTextField custom prompt
- [Datasource] Generic open datasources
- [KWord] Add new remote update module for live string updates support

## 5.2.0

> 2023-10-23

### Breaking Changes

- [VMD] Added contentDescription to Image Contents
- [stream] Changed visibility on several classes to internal.
- [stream] Updated skip function to return Publisher instead of SkipProcessor

## 5.1.0

> 2023-10-05

### Updates

- Ktor `2.3.3`
- AtomicFu `0.22.0`

### Breaking Changes

## 5.0.0

> 2023-09-02

### Updates

- Kotlin `1.9.10`
- Gradle `8.3`
- JDK `17`
- Jetpack Compose Compiler `1.5.3`
- Jetpack Compose Runtime `1.5.0`
- Kotlinx Coroutines `1.7.3`
- Android compile `34`

### Breaking Changes

[viewmodels] Android databindings into its own artefact
_To migrate:_
Add to your android module `implementation("com.mirego.trikot:viewmodels-databinding:$trikot_version")`

[VMD-flow] placeholder(status:progress:placeholderImage) was removed in favour of placeholder(progress, placeholderImage)

## 4.4.0

> 2023-07-17

### Breaking Changes

- Rename `ImageResource(s)` to `TrikotImageResource(s)` to prevent "ambiguous for type lookup in this context" with xCode 15

## 4.3.0

> 2023-03-21

### Breaking Changes

- [All] Old memory model is no longer supported.

### Deprecation

- [Foundation] `MrFreeze` was deprecated. It is no longer needed in new MM.
- [Streams] `Publisher.threadLocal`, `Publishers.frozenSubject` and `Publishers.frozenBehaviorSubject` operators were deprecated. They are no longer needed in new MM.

### Updates

- Kotlin `1.8.10`
- Jetpack Compose Compiler to `1.4.3`
- Jetpack Compose Runtime to `1.3.3`
- Jetpack Compose UI to `1.4.0-beta02`
- Jetpack Compose Material 3 to `1.1.0-alpha07`
- Ktlint to `11.2.0`
- Androidx Lifecycle to `2.6.0-rc01`
- KSP to `1.8.10-1.0.9`
- Android MIN SDK to `24`
- Kotlin Serialization to `1.5.0`
- AtomicFu to `0.20.0`
- Kotlinx Coroutines `1.6.3`

## 4.2.0

- [VMD & VMD-flow] ContentDescription is now a property of VMDImageViewModel.
  It does not exist in VMDImage anymore for android

## 4.1.0

> 2022-12-19

### Updates

- Kotlin to `1.7.20`
- AtomicFu to `0.18.5`
- Jetpack Compose to `1.3.x`
- Ktor to `2.0.3`
- Kotlin Serialization to `1.4.1`
- Kotlin Wrappers extensions to `1.0.1-pre.459`

## 4.0.0

> 2022-12-02

### Breaking Changes

- iOS/tvOS minimum deployment target is now `12.0`
- [datasources] Moved Trikot.Datasources to Trikot.Datasources.streams\
  _To migrate:_
  - Update `com.mirego.trikot:datasources:$trikot_version` for `com.mirego.trikot:datasources-streams:$trikot_version`
  - Add `export("com.mirego.trikot:datasources-core:$trikot_version")`
- [datasources-flow] New implementation that supports flows and coroutines
- [VMD] Removed Modifier.fillMaxWidth in VMDLabeledComponent
  - UI using `VMDCheckbox` and `VMDSwitch` should add fillMaxWidth to the modifier
- [VMD-flow] Removed Modifier.fillMaxWidth in VMDLabeledComponent
  - UI using `VMDCheckbox` and `VMDSwitch` should add fillMaxWidth to the modifier
- [viewmodels] UIView extensions properties are now prefixed with "trikot" on iOS and tvOS
  - `UIButton.buttonViewModel` is now `trikotButtonViewModel`
  - `UIImage.imageViewModel` is now `trikotImageViewModel`
  - `UILabel.labelViewModel` is now `trikotLabelViewModel`
  - `UIPicker.pickerViewModel` is now `trikotPickerViewModel`
  - `UISlider.sliderViewModel` is now `trikotSliderViewModel`
  - `UISwitch.toggleSwitchViewModel` is now `trikotToggleSwitchViewModel`
  - `UITextField.inputTextViewModel` is now `trikotInputTextViewModel`
  - `UITextView.labelViewModel` is now `trikotLabelViewModel`
  - `UIView.viewModel` is now `trikotViewModel`
  - `UIView.trikotViewModel()` is now `getTrikotViewModel()`

### Updates

- [VMD/VMD-flow] Add a DSL in order to ease view model creation
- [VMD/VMD-flow] Snackbar component
- [VMD/VMD-flow] Add Picker component
- [VMD/VMD-flow] Add callback to RemoteImage in VMDImage to know when the resource is loaded on Android
- [VMD-flow] Provide a BasicTextField binding for android
- [VMD-flow] Add support for use of FlowI18N in VMDComponents factory
- [VMD-flow] Backport and fix some functionalities
  [VMD-flow] Improved handling of lifecycle on both android and iOS
- [VMD-compiler] New Gradle plugin to generate declarative Viewmodels boilerplate code
- [analytics] Add distinctAppId to the AnalyticsService
- [datasources] Improvement to BaseHotDataSource
- [datasources] Re-implement with flows and coroutines
- [datasources-flow] Fix pending state while refreshing
- [kword] Add support for dynamic framework resource lookup fallback

### Fixes

- [VMD] Fix Jetpack Compose progress views configuration propagation
- [VMD/VMD-flow] Prevent crashing on too large bitmap in VMDImage on Android
- [VMD-flow] Fix ButtonViewModel setAction staying subscribed
- [VMD-flow] willChange and didChange need to be synchronous to work with the VMDAnimationContext.animationStack
- [viewmodels] Add missing transformation on Android drawable
- [bluetooth] Update Bluetooth permission requirements for Android depending on OS version
- [bluetooth] Make toList conversion thread safe by cloning map before converting its values to list
- [datasources-flow] Change delete/clear cache cancellation behavior

## 3.3.8

> 2022-06-21

- [VMD-flow] New Experimental module that uses Flows and coroutines instead of Publishers

## 3.3.4

> 2022-06-02

- [kword] Trikot Kword now supports the use of Flows
- [kword] BREAKING: To continue using Publishers in your project, please add the following dependency in addition to the usual dependency: `com.mirego.trikot:kword-streams`

## 3.3.2

> 2022-05-02

- [VMD] VMDLazyColumn & VMDLazyRow to replace VMDList with Compose
- [VMD] Updated Coil from 1.4.0 to 2.0.0-rc03

## 3.3.1

> 2022-04-25

- [VMD] Animation engine
- [VMD] Publish sources for compose module

## 3.3.0

> 2022-04-20

Updated:

- AtomicFu from 0.17.0 to 0.17.1
- Jetpack Compose from 1.2.0-alpha03 to 1.2.0-alpha08
- Kotlinx.Coroutines from 1.6.0 to 1.6.1
- Ktor from 2.0.0-beta-1 to 2.0.0
- Kotlin to 1.6.20

## 3.2.1

> 2022-03-22

- [streams] Rename `collect()` operator to `scanWith()`

## 3.2.0

> 2022-03-21

- Now compatible with both memory models

## 3.2.0-M2

> 2022-03-17

- [streams] Added coroutine interop extensions

## 3.2.0-M1

> 2022-01-31

- Enabled experimental kotlin native memory model
- Updated to [Ktor 2.0.0-beta-1](https://github.com/ktorio/ktor/releases/tag/2.0.0-beta-1)
- Reimplemented AtomicReference using [kotlinx.atomicfu](https://github.com/Kotlin/kotlinx.atomicfu)

## 3.1.19

> 2022-03-21

- [streams] Introduce `collect()` operator for Publishers

## 3.1.17

> 2022-03-15

- [kword] Introduce a new `DynamicI18N` concept which expose strings via Publishers. It is implemented via a `MultiLanguageI18N` to react on language changes. When the language switches all publishers will emit the localized string in the new language.
- [kword] Allow loading json strings from multiple sources (paths) via the basePaths parameter.

## 3.1.13

> 2022-02-25

- [viewmodels] Add KingFisher implementation for image handling

## 3.1.12

> 2022-02-21

- Update sample projects to build on iOS arm simulators
- Update Android Gradle Plugin to `7.1.1`
- Update Jetpack Compose to `1.2.0-alpha03`

## 3.1.10

> 2022-02-21

- [VMD] Update VMDImageProvider's signature on iOS

## 3.1.2

> 2022-01-18

- Updated to kotlin 1.6.10
- Downgrade AGP to 7.0.4 to make releases compatible with Arctic Fox
- Update to kotlinx.serialization 1.3.2, ktor 1.6.7, android lifecycle 1.4.0, jetpack compose 1.2.0-alpha01

## 3.0.0

> 2022-01-17

- Migrated to mono repository
- Updated to AGP 7.1.0-rc01 (Android studio bumblebee)
- Updated to gradle 7.3.3
- Moved individual podspecs into a main one with subspecs

Migration:
`gradle.properties`

```
-trikot_foundation_version=2.2.1
-trikot_analytics_version=2.2.0
-trikot_streams_version=2.2.0
-trikot_viewmodels_version=2.2.1
-trikot_http_version=2.2.0
-trikot_kword_version=2.2.0
trikot_version=3.0.0
```

`build.gradle[.kts]`
Use the new trikot_version

`Podfile`

```ruby
pod 'Trikot/http', :git => 'git@github.com:mirego/trikot.git',  :tag => properties['trikot_version'], :inhibit_warnings => true
pod 'Trikot/viewmodels', :git => 'git@github.com:mirego/trikot.git', :tag => properties['trikot_version'], :inhibit_warnings => true
pod 'Trikot/streams', :git => 'git@github.com:mirego/trikot.git', :tag => properties['trikot_version'], :inhibit_warnings => true
pod 'Trikot/kword', :git => 'git@github.com:mirego/trikot.git', :tag => properties['trikot_version'], :inhibit_warnings => true
```

`*.swift`

```ruby
-import Trikot_http
-import Trikot_kword
-import Trikot_viewmodels
import Trikot
```

# 1.X.X to 2.X.X

## Trikot.streams

## 2.2.5

> 2021-10-13

- Added `RepeatablePublisher` to repeat the execution of a block every specified duration
- Updated Kotlin version to 1.5.31
- Updated `trikot.foundation` version to 2.2.2

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`
- Removed deprecation warnings

## Trikot.kword

## 2.3.0

> 2021-11-01

- Updated Kotlin version to `1.5.31`

## 2.2.1

> 2021-07-01

- Fix kotlin-extensions to be compatible with kotlin 1.5.10

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## 2.3.0

> 2021-11-01

- Updated ktor to `1.6.4`
- Updated kotlinx.serialization to `1.3.0` (with fix for https://github.com/Kotlin/kotlinx.serialization/issues/1450)

## 2.2.2

> 2021-10-18

- Updated Kotlin version to 1.5.31
- Updated `trikot.foundation` version to 2.2.2
- Updated `trikot.streams` version to 2.2.5

## 2.2.1

> 2021-10-12

- Integrate custom timeout for specific requests through `RequestBuilder`

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10
- Replace deprecated calls

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## Trikot.http

## 2.3.0

> 2021-11-01

- Updated ktor to `1.6.4`
- Updated kotlinx.serialization to `1.3.0` (with fix for https://github.com/Kotlin/kotlinx.serialization/issues/1450)

## 2.2.2

> 2021-10-18

- Updated Kotlin version to 1.5.31
- Updated `trikot.foundation` version to 2.2.2
- Updated `trikot.streams` version to 2.2.5

## 2.2.1

> 2021-10-12

- Integrate custom timeout for specific requests through `RequestBuilder`

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10
- Replace deprecated calls

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## Trikot.foundation

## 2.2.1

> 2021-10-15

- Updated Kotlin version to `1.5.31`

## 2.2.0

> 2021-06-04

- Added `watchOS` and `macOS` targets
- Updated Kotlin version to `1.5.10`
- Added OS version validation for all supported native targets

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`
- Removed deprecation warnings

## Trikot.viewmodels

## 2.3.1

> 2021-11-09

- Added Web `ApplicationState` foreground/background support using `document.visibilitychange` event

## 2.3.0

> 2021-11-01

- Updated Kotlin version to `1.5.31`

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.1

> 2021-05-14

- Renamed ToggleSwitchViewModel's property `isEnabled` to `enabled` to fix name conflict when combined with the iOS lib of `trikot.analytics`
- Renamed ToggleSwitchViewModel's property `isOn` to `checked` to follow naming convention

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`
- Removed deprecation warnings

## Trikot.graphql

## 2.3.0

> 2021-11-01

- Updated Kotlin version to `1.5.31`
- Updated kotlinx.serialization to `1.3.0` (with fix for https://github.com/Kotlin/kotlinx.serialization/issues/1450)

## 2.2.1

> 2021-10-13

- It is now possible to specify timeout for specific requests
- Updated Kotlin version to 1.5.31

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## Trikot.datasources

## 2.2.1

> 2021-10-28

- Updated Kotlin version to 1.5.31
- Updated `trikot.foundation` version to 2.2.2
- Updated `trikot.streams` version to 2.2.5

## 2.2.0

> 2021-06-08

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## Trikot.bluetooth

## 2.3.0

> 2021-11-01

- Updated Kotlin version to `1.5.31`

## 2.2.0

> 2021-06-10

- Added watchOS and macOS targets
- Updated Kotlin version to 1.5.10

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`

## Trikot.analytics

## 2.3.0

> 2021-11-01

- Updated Kotlin version to `1.5.31`

## 2.2.1

> 2021-06-10

- Added watchOS and macOS targets

## 2.2.0

> 2021-06-09

- Upgraded to Kotlin `1.5.10`
- Fixed module dependency where subproject were linked by SNAPSHOT

## 2.1.0

> 2021-05-10

- Added Project changelog (`CHANGELOG.md`)
- Upgraded to Kotlin `1.5.0`
