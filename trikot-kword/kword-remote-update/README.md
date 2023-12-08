# kword-remote-update

kword-remote-update provides the necessary tools to let applications load pre-downloaded cached translations files, fetch updated online translation files, save them to cache, and instantly update in-app translations.

### Features

- Load pre-downloaded cached translation files to apply them instantly
- Retrieve online translation files (with the format `https://[translations-url]/[configurable-prefix]/[translations-version]/[language-code]/translation.json`) uploaded from Accent translation versions.
- Save downloaded translation files to cache

# kword-remote-update usage

Ideally in the app's bootstrap, instantiate a `KwordRemoteUpdate` object and invoke `setupFileSystem(...)` and `setupRemoteTranslationsSource(...)` based on your requirements.

- if `setupFileSystem(...)` is not called, new translations will not be loaded from cache and will not be stored in cache after being downloaded. Text updates will be discarded once the app is closed.
- if `setupRemoteTranslationsSource(...)` is not called, translations will only be loaded from cache if previously downloaded, but they will not be downloaded again.

Once your kwordRemoteUpdate object set up, call `updateTranslations(...)` to initiate the translations update process.

### Android

```kotlin
val translationsBaseUrl = "https://translations-url.com/translations"
val translationsVersion = "v1.23"
val kwordRemoteUpdate = KwordRemoteUpdate()

kwordRemoteUpdate.setupFileSystem(FileSystem.SYSTEM, applicationContext)
kwordRemoteUpdate.setupRemoteTranslationsSource(translationsBaseUrl, translationsVersion)

val i18N = DefaultI18N().apply {
    AndroidKWord.setCurrentLanguageCode(this, "en") // setup Trikot.Kword
    kwordRemoteUpdate.updateTranslations(this, "en")
}
```

### iOS

```swift
let i18n = DefaultI18N()

let translationsBaseUrl = "https://translations-url.com/translations"
let translationsVersion = "v1.23"
let fileSystem = OkioFileSystem.companion.SYSTEM
let fileManager = FileManager.default
let kwordRemoteUpdate = KwordRemoteUpdate()

kwordRemoteUpdate.setupFileSystem(fileSystem: fileSystem, fileManager: fileManager)
KwordRemoteUpdate.setupRemoteTranslationsSource(translationsUrl: remoteTranslationsUrl, translationsVersion: translationsVersion)

kwordRemoteUpdate.updateTranslationsForLanguage(i18N: i18n, code: SharedBootstrap.currentLanguage().code)
```
