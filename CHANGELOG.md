# Changelog

All notable changes to the library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## Upcoming release

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
