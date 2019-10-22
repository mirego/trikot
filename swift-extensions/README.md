# Trikot.streams swift extensions

- Easily manage Trikot.streams publisher subscription lifecycle. 
- Adds sugar coating to Publisher Subscriptions and DataBinding

## Installation
To use `Trikot.streams` swift extensions, you must export `streams` and `streams-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.streams', :git => 'https://github.com/mirego/trikot.streams.git'
```
Then, run `pod install`.

### Observe
```swift
public func observe<V>(_ publisher: Publisher, toClosure closure: @escaping ((V) -> Void))
```
Subscribe to a publisher and execute the block if the received value can be casted to the block variable type.

In the sample below, subscription will be cancelled automatically when SampleView is freed.

```swift
class SampleView: UIView {
    init(publisher: myPublisher) {
        super.init(frame: .zero)
        observe(myPublisher) { (value: String) in
            print(value)
        }        
    }
}
```

### Bind
```swift
public func bind<T, V>(_ publisher: Publisher, _ keyPath: ReferenceWritableKeyPath<T, V>)
```
Subscribe to a publisher and assign the value to the variable if the received value can be casted to the variable type. 

In the sample below, subscription will be cancelled automatically when SampleView is freed.

```swift
class SampleView: UIView {
    var myVar: String? {
        didSet {
            print(myVar)
        }
    }

    init(publisher: myPublisher) {
        super.init(frame: .zero)
        bind(myPublisher, \.myVar)
    }
}
```

### Unsubscribing
If needed, you can unsubscribe from all publisher by calling `unsubscribeFromAllPublisher`. 
