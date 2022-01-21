# Trikot.viewmodels
ViewModels expose properties that can be binded to Android or iOS UI components. It allows 2 way interactions between Kotlin Multiplatform Common Code and platform UI.

## The basics
Both iOS and Android comes with a binding library that supports basics UI components (View, Label, Button, Input, Image) designed to be bound to their ViewModel equivalent (`ViewModel`, `LabelViewModel`, `ButtonViewModel`, `ImageViewModel`, ect...). Since `ViewModels` are interfaces that are not meant to be modified, Mutable implementations are included to create each type of ViewModel (`MutableViewModel`, `MutableLabelViewModel`, `MutableButtonViewModel`, `MutableImageViewModel`, ect...). We suggest that custom ViewModel definition and implementation follow the same pattern.

All properties are reactive and observed by the platforms. They are ReactiveStreams `Publisher<T>`. See [Trikot.streams](https://github.com/mirego/trikot.streams) for more inforamtion about Publishers.

Additionally, you can easily replace a property with another `org.reactivestreams.Publisher<T>` (Like a Flow Coroutine per example).

See (Sample application)[https://github.com/mirego/trikot.viewmodels/tree/master/sample] for all possibilities.

iOS UILabel             |  Android TextView
:-------------------------:|:-------------------------:
![](./documentation/ios-label.png)  |  ![](./documentation/android-textview.png)

### Interfaces provides documentation
- [ViewModel](https://github.com/mirego/trikot.viewmodels/blob/master/viewmodels/src/commonMain/kotlin/com/mirego/trikot/viewmodels/ViewModel.kt)
- [ButtonViewModel](https://github.com/mirego/trikot.viewmodels/blob/master/viewmodels/src/commonMain/kotlin/com/mirego/trikot/viewmodels/ButtonViewModel.kt)
- [LabelViewModel](https://github.com/mirego/trikot.viewmodels/blob/master/viewmodels/src/commonMain/kotlin/com/mirego/trikot/viewmodels/LabelViewModel.kt)
- [ImageViewModel](https://github.com/mirego/trikot.viewmodels/blob/master/viewmodels/src/commonMain/kotlin/com/mirego/trikot/viewmodels/ImageViewModel.kt)
- [InputTextViewModel](https://github.com/mirego/trikot.viewmodels/blob/master/viewmodels/src/commonMain/kotlin/com/mirego/trikot/viewmodels/InputTextViewModel.kt)


## Multiplatform Sample
#### Common code
```kotlin
class SearchViewModel() {
    private val numberOfClickPublisher = Publishers.behaviorSubject(0)
    private val labelTextPublisher = numberOfClickPublisher.map { "Clicked $it times" }
    private val labelViewModel = MutableLabelViewModel().also {
        it.text = labelTextPublisher
    }
    private val buttonViewModel = MutableButtonViewModel().also {
        it.text = "Click Me".just() // .just() Transform any value into Single Publisher of this value
        it.action.value = ViewModelAction { numberOfClickPublisher.value += 1 }.just()
    }
}
```

#### iOS
See [swift extensions](./swift-extensions/README.md) for more information.

Helps connect a publisher to a variable in a reactive environment.
```kotlin
let label = UILabel()
let button = UIButton()
label.labelViewModel = searchViewModel.labelViewModel
button.buttonViewModel = searchViewModel.buttonViewModel
```

#### Android
```kotlin
 <TextView
            ...
            core:view_model="@{searchViewModel.labelViewModel}"
            app:lifecycleOwnerWrapper="@{lifecycleOwnerWrapper}"
            />
 <Button
            ...
            core:view_model="@{searchViewModel.buttonViewModel}"
            app:lifecycleOwnerWrapper="@{lifecycleOwnerWrapper}"
            />
```

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:viewmodels:$trikot_viewmodels_version"
    jvm "com.mirego.trikot:viewmodels-jvm:$trikot_viewmodels_version"
    js "com.mirego.trikot:viewmodels-js:$trikot_viewmodels_version"
    iosx64 "com.mirego.trikot:viewmodels-iosx64:$trikot_viewmodels_version"
    iosarm64 "com.mirego.trikot:viewmodels-iosarm64:$trikot_viewmodels_version"
```
