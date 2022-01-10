# Trikot.KWord

Trikot.KWord provides the necessary tools to make localisation happen in Kotlin Multiplatform application.

- A gradle plugin that generates Kotlin `enum` from [Accent](https://www.accent.reviews/) localisation files
- Kotlin Multiplatform dependencies to interact with the localisation
- Swift and Android extensions to change current locale to use.

## Plugin usage
```groovy
plugins {
    id 'mirego.kword' version '0.1'
}

kword {
    translationFile 'src/commonMain/resources/translations/translation.fr.json'
    enumClassName 'com.myproject.common.localization.KWordTranslation'
    generatedDir 'src/commonMain/generated'
}

kotlin {
    //...
    sourceSets {
        commonMain {
            dependencies {
                implementation 'org.jetbrains.kotlin:kotlin-stdlib-common'
            }
            kotlin.srcDir(kword.generatedDir)
        }
        //...
     }
}

tasks.findAll { it.name.startsWith('compile') }.each { it.dependsOn('kwordGenerateEnum') }
```

This will generate an enum Named KWordTranslation containing all the keys contained in your translationFile.

# KWord usage

### Common Code

for simple usage:
```kotlin
val myString = KWord[KWordTranslation.HELLO_WORLD]
```

##### For zero/singular/plural usage
To support language with multiple plurals, we adopt the interpolation pattern 

Assuming the following translation key
```json
{
  "plural": "{{count}} keys",
  "plural_0": "No keys",
  "plural_1": "One key",
  "plural_17": "Seventeen keys"
}

```kotlin
// "No keys"
KWord.t(KWordTranslation.PLURAL, 0)

// "One key"
KWord.t(KWordTranslation.PLURAL, 1)

// "2 keys"
KWord.t(KWordTranslation.PLURAL, 2)

// "3 keys"
KWord.t(KWordTranslation.PLURAL, 3)

// "Seventeen keys"
KWord.t(KWordTranslation.PLURAL, 17)
```

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:kword:$trikot_kword_version"
    jvm "com.mirego.trikot:kword-jvm:$trikot_kword_version"
    js "com.mirego.trikot:kword-js:$trikot_kword_version"
    iosx64 "com.mirego.trikot:kword-iosx64:$trikot_kword_version"
    iosarm64 "com.mirego.trikot:kword-iosarm64:$trikot_kword_version"
```

### iOS
See [swift extensions](./swift-extensions/README.md)

### Android
See [android extensions](./android-ktx/README.md)

## License

Trikot.kword is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.kword/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
