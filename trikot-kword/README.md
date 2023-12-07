# Trikot.KWord

Trikot.KWord provides the necessary tools to make localisation happen in Kotlin Multiplatform application.

- A gradle plugin that generates Kotlin `enum` from [Accent](https://www.accent.reviews/) localisation files
- Kotlin Multiplatform dependencies to interact with the localisation
- Swift and Android extensions to change current locale to use.

## Plugin usage

```groovy
plugins {
    id 'mirego.kword' version trikot_version
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
```

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
    dependencies {
        maven { url("https://s3.amazonaws.com/mirego-maven/public") }
    }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:kword:$trikot_version"
                export "com.mirego.trikot:kword-streams:$trikot_version" // If needed for multilingual support
                export "com.mirego.trikot:kword-flow:$trikot_version" // If needed for multilingual support
                export "com.mirego.trikot:kword-remote-update:$trikot_version" // If needed for remote translations update
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:kword:$trikot_version"
                 implementation "com.mirego.trikot:kword-streams:$trikot_version" // If needed for multilingual support
                 implementation "com.mirego.trikot:kword-flow:$trikot_version" // If needed for multilingual support
                 implementation "com.mirego.trikot:kword-remote-update:$trikot_version" // If needed for remote translations update
            }
        }
    }
```

### iOS

See [swift extensions](./swift-extensions/README.md)

# Debug mode

I18N debug mode can be enabled to display the translation keys instead of the translated text.

### Android

for simple usage:

```kotlin
fun setI18N(debugModeEnabled: Boolean = false, i18N: I18N = DefaultI18N(debugModeEnabled)) = i18N.apply {
    AndroidKWord.setCurrentLanguageCode(
        this,
        LanguageUtils.currentLanguage(Locale.getDefault().language).code
    )
}
```

### iOS

for simple usage:

```swift
func configureI18n(debugModeEnabled: Bool) { 
    self.i18n = DefaultI18N(debugMode: isDebugEnabled)
}
```


# Tooling

## Android

When using the Jetpack Compose previewer in Android Studio, there is a specific implementation
of `I18N` that can be used to properly load the translations files.

[PreviewI18N](./kword/src/androidMain/kotlin/com/mirego/trikot/kword/android/PreviewI18N.kt)
