import Foundation
import FirebaseAnalytics
import TRIKOT_FRAMEWORK_NAME

class FirebaseAnalyticsService: AnalyticsService {
    var name: String = "FirebaseAnalytics"
    private var superProperties = [String: Any]()

    func identifyUser(userId: String, properties: [String: Any]) {
        Analytics.setUserID(userId)
        properties.forEach { Analytics.setUserProperty(anyToString($0.value), forName: $0.key) }
    }

    func logout() {
        Analytics.setUserID(nil)
    }

    func setSuperProperties(properties: [String: Any]) {
        properties.forEach { superProperties[$0.key] = $0.value }
    }

    func trackEvent(event: AnalyticsEvent, properties: [String: Any]) {
        var allProperties = [String: Any]()
        properties.forEach { allProperties[$0.key] = $0.value }
        superProperties.forEach { allProperties[$0.key] = $0.value }
        Analytics.logEvent(event.name, parameters: allProperties)
    }

    func unsetAllSuperProperties() {
        superProperties.removeAll()
    }

    func unsetSuperProperties(propertyNames: [String]) {
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
