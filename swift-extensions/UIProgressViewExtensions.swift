import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UIProgressView {
    public var metaProgress: MetaProgress? {
        get { return trikotMetaView() }
        set(value) {
            metaView = value
            if let metaProgress = value {
                observe(metaProgress.percentage) {[weak self] (percentage: Int) in
                    self?.progress = Float(percentage) / 100
                }
            }
        }
    }
}
