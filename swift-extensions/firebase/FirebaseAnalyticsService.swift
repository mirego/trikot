import FirebaseAnalytics
import Foundation
import TRIKOT_FRAMEWORK_NAME

public class FirebaseAnalyticsService: AnalyticsService {
    public var name: String = "FirebaseAnalytics"
    private var superProperties = [String: Any]()

    public init(enableAnalytics: Bool = true) {
        enabled = enableAnalytics
    }

    public var enabled: Bool {
        didSet {
            Analytics.setAnalyticsCollectionEnabled(enabled)
        }
    }

    public func identifyUser(userId: String, properties: [String: Any]) {
        Analytics.setUserID(userId)
        properties.forEach { Analytics.setUserProperty(anyToString($0.value), forName: $0.key) }
    }

    public func incrementUserProperties(incrementalProperties: [String: Any]) {
        // This functionality isn't supported with Firebase Analytics
    }

    public func logout() {
        Analytics.setUserID(nil)
    }

    public func setSuperProperties(properties: [String: Any]) {
        properties.forEach { superProperties[$0.key] = $0.value }
    }

    public func setUserProperties(properties: [String: Any]) {
        properties.forEach { Analytics.setUserProperty(anyToString($0.value), forName: $0.key) }
    }

    public func trackEvent(event: AnalyticsEvent, properties: [String: Any]) {
        var allProperties = [String: Any]()
        properties.forEach { allProperties[$0.key] = $0.value }
        superProperties.forEach { allProperties[$0.key] = $0.value }
        Analytics.logEvent(event.name, parameters: allProperties)
    }

    public func unsetAllSuperProperties() {
        superProperties.removeAll()
    }

    public func unsetSuperProperties(propertyNames: [String]) {
        propertyNames.forEach { superProperties.removeValue(forKey: $0) }
    }

    private func anyToString(_ value: Any) -> String? {
        if let value = value as? String {
            return value
        } else {
            return "\(value)"
        }
    }
}
