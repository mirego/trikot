import Foundation
import TRIKOT_FRAMEWORK_NAME

extension Publisher {
    func asBehaviorSubject() -> BehaviorSubject {
        return self as! BehaviorSubject
    }

    func asSubject() -> PublishSubject {
        return self as! PublishSubject
    }
}

extension Data {
    func toKotlinByteArray() -> KotlinByteArray {
        return ByteArrayNativeUtils().convert(data: self)
    }
}

extension KotlinByteArray {
    func toData() -> Data {
        return ByteArrayNativeUtils().convert(byteArray: self)
    }
}

final class Atomic<T> {
    private let queue = DispatchQueue(label: "Atomic serial queue")
    private var _value: T
    init(_ value: T) {
        self._value = value
    }

    var value: T {
        get {
            return queue.sync { self._value }
        }
    }

    func mutate(_ transform: (inout T) -> ()) {
        queue.sync {
            transform(&self._value)
        }
    }
}
