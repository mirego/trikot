import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UIButton {
    private struct AssociatedKeys {
        static var actionKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    private var tapAction: ViewModelAction? {
        get {
             return objc_getAssociatedObject(self, AssociatedKeys.actionKey) as? ViewModelAction
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.actionKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    public var buttonViewModel: ButtonViewModel? {
        get { return trikotViewModel() }
        set(value) {
            removeTarget(self, action: #selector(onPrimaryActionTriggered), for: .primaryActionTriggered)
            viewModel = value
            if let buttonViewModel = value {
                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isHighlighted) { (button, _) in
                    button.updateBackgroundColor()
                }))

                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isSelected) { (button, _) in
                    button.updateBackgroundColor()
                }))

                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isEnabled) { (button, _) in
                    button.updateBackgroundColor()
                }))

                observe(buttonViewModel.action) { [weak self](tapAction: ViewModelAction) in
                    guard let self = self else { return }
                    self.tapAction = tapAction
                    if tapAction == ViewModelAction.Companion().None {
                        self.removeTarget(self, action: #selector(self.onPrimaryActionTriggered), for: .primaryActionTriggered)
                    } else {
                        self.addTarget(self, action: #selector(self.onPrimaryActionTriggered), for: .primaryActionTriggered)
                    }
                }

                bind(buttonViewModel.enabled, \UIButton.isEnabled)

                bind(buttonViewModel.selected, \UIButton.isSelected)

                observe(buttonViewModel.backgroundImageResource) {[weak self] (imageResourceSelector: StateSelector<ImageResource>) in
                    guard let self = self else { return }

                    self.setBackgroundImageResource(imageResourceSelector.defaultValue(), for: .normal)
                    self.setBackgroundImageResource(imageResourceSelector.selectedValue(), for: .selected)
                    self.setBackgroundImageResource(imageResourceSelector.highlightedValue(), for: .highlighted)
                    self.setBackgroundImageResource(imageResourceSelector.disabledValue(), for: .disabled)
                }

                observe(buttonViewModel.backgroundColor) {[weak self] (selector: StateSelector) in
                    self?.updateBackgroundColor(selector)
                }

                let imageResourceCombineLatest = CombineLatestProcessorExtensionsKt.combine(buttonViewModel.imageResource, publishers: [buttonViewModel.tintColor])
                observe(imageResourceCombineLatest) { [weak self] (value: [Any?]) in
                    guard let self = self else { return }
                    guard let imageSelector = value[0] as? StateSelector<ImageResource>,
                        let tintSelector = value[1] as? StateSelector<Color> else { return }

                    self.setImageResource(imageSelector.defaultValue(), tintColor: tintSelector.defaultValue()?.safeColor(), for: .normal)
                    self.setImageResource(imageSelector.selectedValue(), tintColor: tintSelector.selectedValue()?.safeColor(), for: .selected)
                    self.setImageResource(imageSelector.highlightedValue(), tintColor: tintSelector.highlightedValue()?.safeColor(), for: .highlighted)
                    self.setImageResource(imageSelector.disabledValue(), tintColor: tintSelector.disabledValue()?.safeColor(), for: .disabled)
                }

                if let richText = buttonViewModel.richText {
                    let observable = CombineLatestProcessorExtensionsKt.combine(buttonViewModel.textColor, publishers: [richText])

                    observe(observable) {[weak self] (value: [Any?]) in
                        let colorSelector = value[0] as! StateSelector<Color>
                        let richText = value[1] as! RichText

                        guard let self = self else { return }
                        let font = self.titleLabel?.font ?? UIFont.systemFont(ofSize: 12)
                        let attributedString = self.richTextToAttributedString(richText, referenceFont: font)

                        self.setColoredAttributedTitle(attributedString, colorSelector.defaultValue(), forState: .normal)
                        self.setColoredAttributedTitle(attributedString, colorSelector.selectedValue(), forState: .selected)
                        self.setColoredAttributedTitle(attributedString, colorSelector.highlightedValue(), forState: .highlighted)
                        self.setColoredAttributedTitle(attributedString, colorSelector.disabledValue(), forState: .disabled)

                    }
                } else {
                    observe(buttonViewModel.text) {[weak self] (string: String) in
                        self?.setTitle(string, for: .normal)
                    }

                    observe(buttonViewModel.textColor) {[weak self] (colorSelector: StateSelector<Color>) in
                        if let color = colorSelector.defaultValue()?.safeColor() {
                            self?.setTitleColor(color, for: .normal)
                        }
                        if let color = colorSelector.selectedValue()?.safeColor() {
                            self?.setTitleColor(color, for: .selected)
                        }
                        if let color = colorSelector.disabledValue()?.safeColor() {
                            self?.setTitleColor(color, for: .disabled)
                        }
                        if let color = colorSelector.highlightedValue()?.safeColor() {
                            self?.setTitleColor(color, for: .highlighted)
                        }
                    }
                }

                observe(buttonViewModel.imageAlignment) {[weak self] (alignment: Alignment) in
                    self?.positionSubviews(alignment)
                }
            }
        }
    }

    private func setBackgroundImageResource(_ resource: ImageResource?, for state: UIControl.State) {
        if resource === ImageResourceCompanion().None {
            setBackgroundImage(nil, for: state)
        } else if let image = ImageViewModelResourceManager.shared.image(fromResource: resource) {
            setBackgroundImage(image, for: state)
        }
    }

    private func setImageResource(_ resource: ImageResource?, tintColor: UIColor?, for state: UIControl.State) {
        if resource === ImageResourceCompanion().None {
            setImage(nil, for: state)
        } else if let image = ImageViewModelResourceManager.shared.image(fromResource: resource) {
            setImage(tintColor != nil ? image.imageWithTintColor(tintColor!) : image, for: state)
        }
    }

    private func setColoredAttributedTitle(_ attributedString: NSAttributedString, _ colorSelector: Any?, forState state: UIControl.State) {
        if let color = (colorSelector as? Color)?.safeColor() {
            let coloredAttribuedString = NSMutableAttributedString(attributedString: attributedString)
            coloredAttribuedString.addAttribute(.foregroundColor, value: color, range: NSRange(location: 0, length: attributedString.length))
            setAttributedTitle(coloredAttribuedString, for: state)
        } else if let color = titleColor(for: state) {
            setAttributedTitle(getAttributedString(attributedString, coloringCompletedWith: color), for: state)
        } else {
            setAttributedTitle(attributedString, for: state)
        }
    }
    
    private func getAttributedString(_ attributedString: NSAttributedString, coloringCompletedWith color: UIColor) -> NSAttributedString {
        let coloredAttribuedString = NSAttributedString(string: attributedString.string, attributes: [.foregroundColor: color])
        let completedAttributedString = NSMutableAttributedString(attributedString: coloredAttribuedString)
        attributedString.enumerateAttributes(in: NSRange(location: 0, length: attributedString.length), options: []) { (attributes, range, _) in
            if !attributes.isEmpty {
                completedAttributedString.addAttributes(attributes, range: range)
            }
        }
        return completedAttributedString
    }

    public func positionSubviews(_ alignment: Alignment?) {
        guard let buttonViewModel = buttonViewModel else { return }
        guard let alignment = alignment, [Alignment.right, Alignment.left].contains(alignment) else { resetAlignment() ; return }

        observe(buttonViewModel.imageResource.first()) {[weak self] (imageSelector: StateSelector<ImageResource>) in

            guard let titleLabel = self?.titleLabel, let image = ImageViewModelResourceManager.shared.image(fromResource: imageSelector.defaultValue()) else { return }

            titleLabel.sizeToFit()

            var titleValue: CGFloat = 0
            var imageValue: CGFloat = 0
            self?.contentHorizontalAlignment = .center
            switch alignment {
            case .right:
                titleValue = -(image.size.width)
                imageValue = titleLabel.bounds.width
            case .left:
                self?.contentHorizontalAlignment = .left
            default:
                break
            }
            self?.imageEdgeInsets = UIEdgeInsets(top: 0, left: imageValue, bottom: 0, right: -imageValue)
            self?.titleEdgeInsets = UIEdgeInsets(top: 0, left: titleValue, bottom: 0, right: -titleValue)

            DispatchQueue.main.async {
                self?.superview?.setNeedsLayout()
            }
        }
    }

    func resetAlignment() {
        imageEdgeInsets = .zero
        titleEdgeInsets = .zero
    }

    func updateBackgroundColor(_ backgroundColorSelector: StateSelector<Color>) {
        if !isEnabled, let buttonBackgroundColor = backgroundColorSelector.disabledValue()?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if isHighlighted, let buttonBackgroundColor = backgroundColorSelector.highlightedValue()?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if isSelected, let buttonBackgroundColor = backgroundColorSelector.selectedValue()?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if let buttonBackgroundColor = backgroundColorSelector.defaultValue()?.safeColor() {
            backgroundColor = buttonBackgroundColor
        }
    }

    func updateBackgroundColor() {
        guard let buttonViewModel = buttonViewModel else { return }
        observe(buttonViewModel.backgroundColor.first()) {[weak self] (selector: StateSelector) in
            self?.updateBackgroundColor(selector)
        }
    }

    @objc
    private func onPrimaryActionTriggered() {
        guard let tapAction = tapAction else { return }
        tapAction.execute(actionContext: self)
    }
}
