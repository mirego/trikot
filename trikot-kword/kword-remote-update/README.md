# kword-remote-update

kword-remote-update provides the necessary tools to let applications load pre-downloaded cached translations files, fetch updated online translation files, save them to cache, and instantly update in-app translations.

### Features

- Load pre-downloaded cached translation files to apply them instantly
- Retrieve online translation files (with the format `https://[translations-url]/[configurable-prefix]/[translations-version]/translation.[languageCode].json`) uploaded from Accent translation versions.
- Save downloaded translation files to cache

# kword-remote-update usage

Before updating the translations with the `KwordRemoteUpdate` object, `KwordRemoteUpdate.setupFileSystem(...)` and `KwordRemoteUpdate.setupRemoteTranslationsSource(...)` might need to be called depending on your use case.

- if `KwordRemoteUpdate.setupFileSystem(...)` is not called, translations will not be loaded from cache and will not be saved to cache once downloaded.
- if `KwordRemoteUpdate.setupRemoteTranslationsSource(...)` is not called, translations will only be loaded from cache, and nothing will be downloaded or saved.

### Android

```kotlin
val translationsBaseUrl = "https://apptranslations.com/translations"
val translationsVersion = "v1.23"
KwordRemoteUpdate.setupFileSystem(FileSystem.SYSTEM, applicationContext)
KwordRemoteUpdate.setupRemoteTranslationsSource(translationsBaseUrl, translationsVersion)

val i18N = DefaultI18N().apply {
    AndroidKWord.setCurrentLanguageCode(this, "en") // setup Trikot.Kword
    KwordRemoteUpdate.updateTranslations(this, "en")
}
```

### iOS
```swift
let translationsBaseUrl = "https://apptranslations.com/translations"
let translationsVersion = "v1.23"
let fileSystem = OkioFileSystem.companion.SYSTEM
let fileManager = FileManager.default
        
KwordRemoteUpdate.shared.setupFileSystem(fileSystem: fileSystem, fileManager: fileManager)
KwordRemoteUpdate.shared.setupRemoteTranslationsSource(translationsUrl: remoteTranslationsUrl, appVersion: translationsVersion)

KwordRemoteUpdate.shared.updateTranslationsForLanguage(i18N: i18n, code: SharedBootstrap.currentLanguage().code)
```