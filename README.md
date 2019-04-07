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
