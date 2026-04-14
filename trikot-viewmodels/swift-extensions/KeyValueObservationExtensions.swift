import Foundation
import SampleTrikotFrameworkName

class KeyValueObservationHolder: NSObject, Cancellable {
    let kvo: NSKeyValueObservation

    init(_ kvo: NSKeyValueObservation) {
        self.kvo = kvo
    }

    public func cancel() {
        kvo.invalidate()
    }
}
