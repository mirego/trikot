# Changelog

All notable changes to the library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## Upcoming

> TBD

### Breaking Changes

- [VMD] ContentDescription is now a property of VMDImageViewModel.
  - It does not exist in VMDImage anymore for android
- [VMD-flow] ContentDescription is now a property of VMDImageViewModel.
  - It does not exist in VMDImage anymore for android
- [All] Old memory model is no longer supported.

### Deprecation

- [Foundation] MrFreeze was deprecated. It is no longer needed in new MM.
- [Streams] `Publisher.threadLocal`, `Publishers.frozenSubject` and `Publishers.frozenBehaviorSubject` operators were removed. They are no longer needed in new MM.

### Updates

- Kotlin `1.8.10`
- Jetpack Compose Compiler to `1.4.3`
- Jetpack Compose Runtime to `1.3.3`
- Jetpack Compose UI to `1.3.1`
- Jetpack Compose Material 3 to `1.1.0-alpha07`
- Ktlint to `11.2.0`
- Androidx Lifecycle to `2.6.0-rc01`
- KSP to `1.8.10-1.0.9`

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
