import Trikot_viewmodels
import TRIKOT_FRAMEWORK_NAME

class SampleTextAppearanceResourceProvider: TextAppearanceViewModelResourceProvider {
    func textAppearance(fromResource resource: TextAppearanceResource) -> TextAppearanceAttributes? {
        if let sampleResource = resource as? SampleTextAppearanceResource {
            switch sampleResource {
            case .textAppearanceBold:
                return TextAppearanceAttributes(attributes: [.font: UIFont.boldSystemFont(ofSize: 12), .foregroundColor: UIColor.black])
            case .textAppearanceItalic:
                return TextAppearanceAttributes(attributes: [.font: UIFont.italicSystemFont(ofSize: 12), .foregroundColor: UIColor.black])
            case .textAppearanceColored:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 12), .foregroundColor: UIColor.green])
            case .textAppearanceGrayed:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 12), .foregroundColor: UIColor.gray])
            case .textAppearanceHighlighted:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 12), .foregroundColor: UIColor.black, .backgroundColor: UIColor.yellow])
            case .textAppearanceBold:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 12), .foregroundColor: UIColor.black, .backgroundColor: UIColor.yellow])
            case .textAppearanceSuperscript:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 7), .foregroundColor: UIColor.black, .baselineOffset: 6])
            default:
                return TextAppearanceAttributes(attributes: [.font: UIFont.systemFont(ofSize: 12), .foregroundColor: UIColor.black])
            }
        } else {
            return TextAppearanceAttributes()
        }
    }
}
