import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public extension VMDAnimation {
    var animation: SwiftUI.Animation? {
        if let tweenAnimation = self as? VMDAnimationTween {
            if let standardEasing = tweenAnimation.easing as? VMDAnimationEasingStandard {
                switch standardEasing {
                case .linear:
                    return .linear(duration: tweenAnimation.durationInSeconds).delay(tweenAnimation.delayInSeconds)
                case .easein:
                    return .easeIn(duration: tweenAnimation.durationInSeconds).delay(tweenAnimation.delayInSeconds)
                case .easeout:
                    return .easeOut(duration: tweenAnimation.durationInSeconds).delay(tweenAnimation.delayInSeconds)
                case .easeineaseout:
                    return .easeInOut(duration: tweenAnimation.durationInSeconds).delay(tweenAnimation.delayInSeconds)
                default:
                    return .default.delay(tweenAnimation.delayInSeconds)
                }
            } else if let customEasing = tweenAnimation.easing as? VMDAnimationEasingCubicBezier {
                return .timingCurve(Double(customEasing.a), Double(customEasing.b), Double(customEasing.c), Double(customEasing.d), duration: tweenAnimation.durationInSeconds)
            }
        } else if let springAnimation = self as? VMDAnimationSpring {
            return .spring(response: springAnimation.response, dampingFraction: Double(springAnimation.dampingRatio))
        }

        return nil
    }
}
