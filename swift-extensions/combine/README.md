# Trikot.streams swift combine extensions

- Easily manage Trikot.streams publisher with swiftUI. 

## Installation
To use `Trikot.streams` swift combine extensions, you must export `streams` and `streams-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.streams/Combine', :git => 'https://github.com/mirego/trikot.streams.git'
```
Then, run `pod install`.

### Observe
```swift
struct FeedView: View {
    private let kotlinPublisher = CommonCode.getStringPublisher()
    private let combinePublisher = kotlinPublisher.asCombinePublisher(type: String.self)
    
    @State private var text = ""
    
    var body: some View {
        Text(self.text)
            .onReceive(combinePublisher) { self.text = $0 }
    }
}
```
