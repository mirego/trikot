# Trikot.metaviews

Functionnal Meta abstraction of visual components.

Kotlin multiplatform [Trikot.streams](https://github.com/mirego/trikot.streams) 

## Sample
#### Common code
```kotlin
class SearchViewModel() {
    private val labelTextPublisher = Publishers.behaviorSubject("Not clicked")
    private val metaLabel = MutableMetaLabel().also {
        it.text = labelTextPublisher
    }
    private val metaButton = MutableMetaButton().also {
        it.text = "Click Me".just() // Transform text into Single value publisher
        it.onTap.value = MetaAction { labelTextPublisher.value = "Clicked" }
    }
}
```

### Interfaces provides documentation
- [MetaView](https://github.com/mirego/trikot.metaviews/blob/master/metaviews/src/commonMain/kotlin/com/mirego/trikot/metaviews/MetaView.kt)
- [MetaButton](https://github.com/mirego/trikot.metaviews/blob/master/metaviews/src/commonMain/kotlin/com/mirego/trikot/metaviews/MetaButton.kt)
- [MetaLabel](https://github.com/mirego/trikot.metaviews/blob/master/metaviews/src/commonMain/kotlin/com/mirego/trikot/metaviews/MetaLabel.kt)
- [MetaImage](https://github.com/mirego/trikot.metaviews/blob/master/metaviews/src/commonMain/kotlin/com/mirego/trikot/metaviews/MetaImage.kt)
- [MetaInputText](https://github.com/mirego/trikot.metaviews/blob/master/metaviews/src/commonMain/kotlin/com/mirego/trikot/metaviews/MetaInputText.kt)


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
            app:lifecycleOwnerWrapper="@{lifecycleOwnerWrapper}"
            />
 <Button
            ...
            core:meta_view="@{searchViewModel.metaButton}"
            app:lifecycleOwnerWrapper="@{lifecycleOwnerWrapper}"
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
