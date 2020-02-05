import Foundation
import TRIKOT_FRAMEWORK_NAME

func frozenBehaviorSubject(value: Any? = nil) -> Publisher {
    let publisher = Publishers().behaviorSubject(value: value)
    MrFreeze().freeze(objectToFreeze: publisher)
    return publisher
}

func frozenSubject(value: Any? = nil) -> Publisher {
    let publisher = Publishers().publishSubject()
    MrFreeze().freeze(objectToFreeze: publisher)
    return publisher
}

extension Publisher {
    func asBehaviorSubject() -> BehaviorSubject {
        return self as! BehaviorSubject
    }

    func asSubject() -> PublishSubject {
        return self as! PublishSubject
    }
}
