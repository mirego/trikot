import Foundation
import Trikot
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    public static let translationBasePaths = [
        "translation"
    ]
    
    public static let shared = TrikotKword()
    
    private var fileSystem: FileSystem? = nil
    private var storageDirectoryPath: String? = nil

    private var remoteTranslationsUrl: String? = nil
    private var appVersion: String? = nil
    
    private func setupFileSystem(
        fileSystem: FileSystem,
        fileManager: FileManager
    ) {
        self.fileSystem = fileSystem
        storageDirectoryPath = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first?.relativePath
    }
    
    private func setupRemoteTranslationSource(
        translationsUrl: String,
        appVersion: String
    ) {
        remoteTranslationsUrl = translationsUrl
        self.appVersion = appVersion
    }

    public func setCurrentLanguage(_ languageCode: String, basePaths: [String] = TrikotKword.translationBasePaths) {
        setCurrentLanguage(i18N: KWord.shared, basePaths: basePaths, codes: languageCode)
    }

    public func setCurrentLanguage(
        i18N: I18N,
        languageCode: String,
        basePaths: [String] = TrikotKword.translationBasePaths
    ) {
        setCurrentLanguage(i18N: i18N, basePaths: basePaths, codes: languageCode)
    }

    public func setCurrentLanguage(
        i18N: I18N,
        basePaths: [String] = TrikotKword.translationBasePaths,
        codes: String...
    ) {
        KwordLoader.shared.setCurrentLanguageCodes(
            i18N: i18N, 
            basePaths: basePaths,
            fileSystem: fileSystem,
            cacheDirectoryPath: storageDirectoryPath,
            translationFileUrl: remoteTranslationsUrl,
            appVersion: appVersion,
            codes: KotlinArray<NSString>(size: Int32(codes.count)) { index in
            codes[index.intValue] as NSString
            }
        )
    }
}
