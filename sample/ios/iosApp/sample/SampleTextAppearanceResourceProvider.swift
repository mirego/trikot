import Trikot_viewmodels
import ViewModelsSample

class SampleTextAppearanceResourceProvider: TextAppearanceViewModelResourceProvider {
    func textAppearance(fromResource resource: TextAppearanceResource) -> TextAppearanceAttributes? {
        if let sampleResource = resource as? SampleTextAppearanceResource {
            switch sampleResource {
            case .textAppearanceBold:
                return TextAppearanceAttributes(font: .boldSystemFont(ofSize: 12), foregroundColor: .black)
            case .textAppearanceItalic:
                return TextAppearanceAttributes(font: .italicSystemFont(ofSize: 12), foregroundColor: .black)
            case .textAppearanceColored:
                return TextAppearanceAttributes(font: .systemFont(ofSize: 12), foregroundColor: .green)
            case .textAppearanceGrayed:
                return TextAppearanceAttributes(font: .systemFont(ofSize: 12), foregroundColor: .gray)
            case .textAppearanceHighlighted:
                return TextAppearanceAttributes(font: .systemFont(ofSize: 12), foregroundColor: .black, backgroundColor: .yellow)
            default:
                return TextAppearanceAttributes(font: .systemFont(ofSize: 12), foregroundColor: .black)
            }
        } else {
            return nil
        }
    }
}
