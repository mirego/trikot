import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UIButton {
    public var metaButton: MetaButton? {
        get { return trikotMetaView() }
        set(value) {
            removeTarget(self, action: #selector(onButtonTouchUp), for: .touchUpInside)
            metaView = value
            if let metaButton = value {
                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isHighlighted) { (button, _) in
                    button.updateBackgroundColor()
                }))

                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isSelected) { (button, _) in
                    button.updateBackgroundColor()
                }))

                trikotInternalPublisherCancellableManager.add(cancellable: KeyValueObservationHolder(self.observe(\UIButton.isEnabled) { (button, _) in
                    button.updateBackgroundColor()
                }))

                addTarget(self, action: #selector(onButtonTouchUp), for: .touchUpInside)

                bind(metaButton.enabled, \UIButton.isEnabled)

                bind(metaButton.selected, \UIButton.isSelected)

                observe(metaButton.backgroundImageResource) {[weak self] (imageResourceSelector: MetaSelector) in
                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageResourceSelector.default_ as? ImageResource)) {
                        self?.setBackgroundImage(image, for: .normal)
                    }

                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageResourceSelector.selected as? ImageResource)) {
                        self?.setBackgroundImage(image, for: .selected)
                    }

                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageResourceSelector.disabled as? ImageResource)) {
                        self?.setBackgroundImage(image, for: .disabled)
                    }

                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageResourceSelector.highlighted as? ImageResource)) {
                        self?.setBackgroundImage(image, for: .highlighted)
                    }
                }

                observe(metaButton.backgroundColor) {[weak self] (selector: MetaSelector) in
                    self?.updateBackgroundColor(selector)
                }

                let imageResourceCombineLatest = CombineLatest.Companion().combine2(pub1: metaButton.imageResource, pub2: metaButton.tintColor)
                observe(imageResourceCombineLatest) { [weak self] (result: CombineLatestResult2) in
                    guard let imageSelector = result.component1() as? MetaSelector,
                        let tintSelector = result.component2() as? MetaSelector
                        else { return }

                    var tintColor = (tintSelector.defaultValue() as Color?)?.safeColor()
                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageSelector.defaultValue() as ImageResource?)) {
                        self?.setImage(tintColor != nil ? image.imageWithTintColor(tintColor!) : image, for: .normal)
                    }

                    tintColor = (tintSelector.selectedValue() as Color?)?.safeColor()
                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageSelector.selectedValue() as ImageResource?)) {
                        self?.setImage(tintColor != nil ? image.imageWithTintColor(tintColor!) : image, for: .selected)
                    }

                    tintColor = (tintSelector.highlightedValue() as Color?)?.safeColor()
                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageSelector.highlightedValue() as ImageResource?)) {
                        self?.setImage(tintColor != nil ? image.imageWithTintColor(tintColor!) : image, for: .highlighted)
                    }

                    tintColor = (tintSelector.disabledValue() as Color?)?.safeColor()
                    if let image = MetaImageResourceManager.shared.image(fromResource: (imageSelector.disabledValue() as ImageResource?)) {
                        self?.setImage(tintColor != nil ? image.imageWithTintColor(tintColor!) : image, for: .disabled)
                    }
                }

                if let richText = metaButton.richText {
                    let observable = CombineLatestProcessorExtensionsKt.combine(metaButton.textColor, publishers: [richText])

                    observe(observable) {[weak self] (value: [Any?]) in
                        let colorSelector = value[0] as! MetaSelector
                        let richText = value[1] as! RichText

                        guard let self = self else { return }
                        let font = self.titleLabel?.font ?? UIFont.systemFont(ofSize: 12)
                        let attributedString = self.richTextToAttributedString(richText, referenceFont: font)

                        self.setColoredAttributedTitle(attributedString, colorSelector.default_, forState: .normal)
                        self.setColoredAttributedTitle(attributedString, colorSelector.selected, forState: .selected)
                        self.setColoredAttributedTitle(attributedString, colorSelector.disabled, forState: .disabled)
                        self.setColoredAttributedTitle(attributedString, colorSelector.highlighted, forState: .highlighted)
                    }
                } else {
                    observe(metaButton.text) {[weak self] (string: String) in
                        self?.setTitle(string, for: .normal)
                    }

                    observe(metaButton.textColor) {[weak self] (colorSelector: MetaSelector) in
                        if let color = (colorSelector.default_ as? Color)?.safeColor() {
                            self?.setTitleColor(color, for: .normal)
                        }
                        if let color = (colorSelector.selected as? Color)?.safeColor() {
                            self?.setTitleColor(color, for: .selected)
                        }
                        if let color = (colorSelector.disabled as? Color)?.safeColor() {
                            self?.setTitleColor(color, for: .disabled)
                        }
                        if let color = (colorSelector.highlighted as? Color)?.safeColor() {
                            self?.setTitleColor(color, for: .highlighted)
                        }
                    }
                }

                observe(metaButton.imageAlignment) {[weak self] (alignment: Alignment) in
                    self?.positionSubviews(alignment)
                }
            }
        }
    }

    private func setColoredAttributedTitle(_ attributedString: NSAttributedString, _ colorSelector: Any?, forState state: UIControl.State) {
        if let color = (colorSelector as? Color)?.safeColor() {
            let coloredAttribuedString = NSMutableAttributedString(attributedString: attributedString)
            coloredAttribuedString.addAttribute(.foregroundColor, value: color, range: NSRange(location: 0, length: attributedString.length))
            self.setAttributedTitle(coloredAttribuedString, for: state)
        }
    }

    public func positionSubviews(_ alignment: Alignment?) {
        guard let metaButton = metaButton else { return }
        guard let alignment = alignment, [Alignment.right, Alignment.left].contains(alignment) else { resetAlignment() ; return }

        observe(metaButton.imageResource.first()) {[weak self] (imageSelector: MetaSelector) in

            guard let titleLabel = self?.titleLabel, let image = MetaImageResourceManager.shared.image(fromResource: imageSelector.defaultValue() as ImageResource?) else { return }

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

    func updateBackgroundColor(_ backgroundColorSelector: MetaSelector) {
        if !isEnabled, let buttonBackgroundColor = (backgroundColorSelector.disabledValue() as Color?)?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if isHighlighted, let buttonBackgroundColor = (backgroundColorSelector.highlightedValue() as Color?)?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if isSelected, let buttonBackgroundColor = (backgroundColorSelector.selectedValue() as Color?)?.safeColor() {
            backgroundColor = buttonBackgroundColor
        } else if let buttonBackgroundColor = (backgroundColorSelector.defaultValue() as Color?)?.safeColor() {
            backgroundColor = buttonBackgroundColor
        }
    }

    func updateBackgroundColor() {
        guard let metaButton = metaButton else { return }
        observe(metaButton.backgroundColor.first()) {[weak self] (selector: MetaSelector) in
            self?.updateBackgroundColor(selector)
        }
    }

    @objc
    private func onButtonTouchUp() {
        guard let metaButton = metaButton else { return }
        observe(metaButton.onTap.first()) {[weak self] (value: MetaAction) in value.execute(actionContext: self ?? nil) }
    }
}
