import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    public static let shared = TrikotKword()

    public func setCurrentLanguage(_ languageCode: String) {
        do {
            let translationBundle = Bundle(for: KWord.self)
            let contents = try String(contentsOfFile: translationBundle.path(forResource: "translation.\(languageCode)", ofType: "json") ?? "")
            let decoder = JSONDecoder()
            let strings = try decoder.decode([String: String].self, from: contents.data(using: .utf8)!)
            KWord().changeLocaleStrings(strings: strings)
        } catch let error {
            print("Unable to load translation: \(error)")
        }
    }
}
