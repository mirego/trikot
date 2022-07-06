import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UIPickerView {
    private struct AssociatedKeys {
        static var elementsKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    private var elements: [String]? {
        get {
             return objc_getAssociatedObject(self, AssociatedKeys.elementsKey) as? [String]
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.elementsKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }
    
    public var trikotPickerViewModel: PickerViewModel? {
        get { return getTrikotViewModel() }
        set(value) {
            trikotViewModel = value
            
            guard let pickerViewModel = value else { return }

            self.delegate = self
            
            let pickerViewModelCombineLatest = CombineLatestProcessorExtensionsKt.combine(pickerViewModel.elements, publishers: [pickerViewModel.selectedElementIndex])
            
            observe(pickerViewModelCombineLatest) { [weak self] (value: [Any?]) in
                guard let elements = value[0] as? [PickerItemViewModel],
                      let selectedIndex = value[1] as? Int else { return }
                
                self?.elements = elements.map {
                    $0.displayName
                }
                
                self?.selectRow(selectedIndex, inComponent: 0, animated: false)
                
            }
            
            bind(pickerViewModel.enabled, \UIPickerView.isUserInteractionEnabled)
        }
    }
}

extension UIPickerView : UIPickerViewDelegate {
    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.trikotPickerViewModel?.setSelectedElementIndex(index: Int32(row))
    }
}

extension UIPickerView: UIPickerViewDataSource {
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return elements?.count ?? 0
    }
    
    public func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return elements?[row] ?? nil
    }
}
