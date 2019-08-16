# KWord

## Incubating!
Currently only the enum generator in here. Runtime part not extracted in lib yet.

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

## Sample usage

##### Import dependencies
```groovy
    api "com.mirego.trikot:kword:$trikot_kword_version"
    jvm "com.mirego.trikot:kword-jvm:$trikot_kword_version"
    js "com.mirego.trikot:kword-js:$trikot_kword_version"
    iosx64 "com.mirego.trikot:kword-iosx64:$trikot_kword_version"
    iosarm64 "com.mirego.trikot:kword-iosarm64:$trikot_kword_version"
```

### Common Code
```kotlin
val myString = KWord[KWordTranslation.HELLO_WORLD]
```

### iOS
To use `Trikot.kword` on iOS, you must export `kword` and `kword-iosx64` module in your framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
 pod 'Trikot.kword', :git => 'git@github.com:mirego/trikot.kword.git'
```

##### Set the current language of your application
The following sample will load the resource file `translation.en.json` and use it as current language.
```swift
import Trikot_kword
...
TrikotKword.shared.setCurrentLanguage("en")
```

### Android
**TODO**
