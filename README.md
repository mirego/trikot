<div align="center">
  <img src="https://user-images.githubusercontent.com/11348/158435852-861b0ea1-b895-452b-abd4-029ccd6f5b86.svg" width="200" />
  <p>Trikot is a framework that helps building Kotlin Multiplatform applications.<br />iOS, Android and Web are the primary targets.</p>
  <a href="https://github.com/mirego/trikot/tags"><img src="https://img.shields.io/github/tag/mirego/trikot.svg?label=Latest%20version"></a>
  <a href="http://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin-1.6.10-blue.svg?logo=kotlin" /></a>
  <a href="https://github.com/mirego/trikot/actions/workflows/ci.yml"><img src="https://github.com/mirego/trikot/actions/workflows/ci.yml/badge.svg" /></a>
  <a href="https://opensource.org/licenses/BSD-3-Clause"><img src="https://img.shields.io/badge/License-BSD_3--Clause-blue.svg" /></a>
</div>

# Trikot / Kotlin Multiplatform libraries

## Table of contents

- [Modules](#modules)
- [Samples](#samples)
- [License](#license)

## Modules

| Module                                                           | Description                                                         |
| ---------------------------------------------------------------- | ------------------------------------------------------------------- |
| [trikot.foundation](./trikot-foundation)                         | Foundation classes and building blocks                              |
| [trikot.streams](./trikot-streams)                               | Reactive streams                                                    |
| [trikot.datasources](./trikot-datasources)                       | Cascading data access layers abstraction.                           |
| [trikot.http](./trikot-http)                                     | Multiplatform http client with native platform implementations.     |
| [trikot.graphql](./trikot-graphql)                               | GraphQL query client built over trikot.http and trikot.datasources. |
| [trikot.analytics](./trikot-analytics)                           | Android and iOS analytics providers.                                |
| [trikot.bluetooth](./trikot-bluetooth)                           | Android and iOS bluetooth.                                          |
| [trikot.kword](./trikot-kword)                                   | i18N with code generation for string keys.                          |
| [trikot.viewmodels](./trikot-viewmodels)                         | ViewModels for imperative frameworks (Android views and UIKit).     |
| [trikot.viewmodels.declarative](./trikot-viewmodels-declarative) | ViewModels for declarative framework (Jetpack compose and SwiftUI). |

## Samples

- [trikot.patron](https://github.com/mirego/trikot.patron) is our boilerplate project. It uses a couple of trikot modules.
- [trikot.viewmodel samples](./trikot-viewmodels/sample) are our samples specific to this module.
- [trikot.viewmodel.declarative samples](./trikot-viewmodels-declarative/sample) are our samples specific to this module.

## License

Trikot is © 2018-2022 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
