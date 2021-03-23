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
    
    public var pickerViewModel: PickerViewModel? {
        get { return trikotViewModel() }
        set(value) {
            viewModel = value
            
            guard let pickerViewModel = value else { return }

            self.delegate = self
            observe(pickerViewModel.selectedElementIndex) { [weak self] (value: Int) in
                self?.selectRow(value, inComponent: 0, animated: false)
            }

            observe(pickerViewModel.elements) { [weak self] (value: [PickerItemViewModel]) in
                self?.elements = value.map {
                    $0.displayName
                }
            }
            
            bind(pickerViewModel.enabled, \UIPickerView.isUserInteractionEnabled)
        }
    }
}

extension UIPickerView : UIPickerViewDelegate {
    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.pickerViewModel?.setSelectedElementIndex(index: Int32(row))
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
