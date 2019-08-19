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

# KWord usage

### Common Code
```kotlin
val myString = KWord[KWordTranslation.HELLO_WORLD]
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
