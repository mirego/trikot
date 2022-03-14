import Foundation
import Trikot
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    private static let translationBasePaths = [
        "translation"
    ]
    
    public static let shared = TrikotKword()

    public func setCurrentLanguage(_ languageCode: String) {
        KwordLoader.shared.setCurrentLanguageCode(code: languageCode, basePaths: TrikotKword.translationBasePaths)
    }

    public func setCurrentLanguage(i18N: I18N, languageCode: String) {
        KwordLoader.shared.setCurrentLanguageCode(i18N: i18N, basePaths: TrikotKword.translationBasePaths, code: languageCode)
    }

    public func setCurrentLanguage(i18N: I18N, codes: String...) {
        KwordLoader.shared.setCurrentLanguageCodes(i18N: i18N, basePaths: TrikotKword.translationBasePaths, codes: KotlinArray<NSString>(size: Int32(codes.count)) { index in
            codes[index.intValue] as NSString
        })
    }
}

