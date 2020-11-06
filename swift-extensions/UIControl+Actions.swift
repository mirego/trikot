import UIKit

private class ActionTrampoline<T>: NSObject {
    private let action: (T) -> Void

    init(action: @escaping (T) -> Void) {
        self.action = action
    }

    @objc func exectuteControlAction(sender: UIControl) {
        action(sender as! T)
    }
}

private var UIControlActionAssociatedObjectKeys: [UInt: UnsafeMutablePointer<Int8>] = [:]

protocol UIControlActionFunctionProtocol {}
extension UIControl: UIControlActionFunctionProtocol {}

extension UIControlActionFunctionProtocol where Self: UIControl {
    func addAction(events: UIControl.Event, _ action: @escaping (Self) -> Void) {
        let trampoline = ActionTrampoline(action: action)
        addTarget(trampoline, action: #selector(trampoline.exectuteControlAction(sender:)), for: events)
        objc_setAssociatedObject(self, actionKey(forEvents: events), trampoline, .OBJC_ASSOCIATION_RETAIN)
    }

    func removeAction(events: UIControl.Event) {
        objc_setAssociatedObject(self, actionKey(forEvents: events), nil, .OBJC_ASSOCIATION_RETAIN)
    }

    private func actionKey(forEvents events: UIControl.Event) -> UnsafeMutablePointer<Int8> {
        if let key = UIControlActionAssociatedObjectKeys[events.rawValue] {
            return key
        } else {
            let key = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
            UIControlActionAssociatedObjectKeys[events.rawValue] = key
            return key
        }
    }
}
