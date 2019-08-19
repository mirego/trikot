# Trikot.metaviews

Meta abstraction of visual components

## Sample
#### Common code
```kotlin
class SearchViewModel() {
    private val metaLabel = MutableMetaLabel().also {
        it.text.value = "Not clicked"
    }
    private val metaButton = MutableMetaButton().also {
        it.text.value = "Click Me"
        it.onTap.value = MetaAction { titleLabel.text.value = "Clicked" }
    }
}
```

#### iOS
See [swift extensions](./swift-extensions/README.md) for more information.

Helps connect a publisher to a variable in a reactive environment.
```kotlin
let label = UILabel()
let button = UIButton()
label.metaLabel = searchViewModel.metaLabel
button.metaButton = searchViewModel.metaButton
```

#### Android
See [android extensions](./android-ktx/README.md) for more information.
```kotlin
 <TextView
            ...
            core:meta_view="@{searchViewModel.metaLabel}"
            />
 <Button
            ...
            core:meta_view="@{searchViewModel.metaButton}"
            />
```

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:metaviews:$trikot_metaviews_version"
    jvm "com.mirego.trikot:metaviews-jvm:$trikot_metaviews_version"
    js "com.mirego.trikot:metaviews-js:$trikot_metaviews_version"
    iosx64 "com.mirego.trikot:metaviews-iosx64:$trikot_metaviews_version"
    iosarm64 "com.mirego.trikot:metaviews-iosarm64:$trikot_metaviews_version"
```
