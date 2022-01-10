import TRIKOT_FRAMEWORK_NAME

extension StateSelector where T == Color {
    public func defaultValue() -> T? {
        return stateSelectorDefaultValue(self)
    }

    public func highlightedValue() -> T? {
        return stateSelectorHighlightedValue(self)
    }

    public func selectedValue() -> T? {
        return stateSelectorSelectedValue(self)
    }

    public func disabledValue() -> T? {
        return stateSelectorDisabledValue(self)
    }
}

extension StateSelector where T == ImageResource {
    public func defaultValue() -> T? {
        return stateSelectorDefaultValue(self)
    }

    public func highlightedValue() -> T? {
        return stateSelectorHighlightedValue(self)
    }

    public func selectedValue() -> T? {
        return stateSelectorSelectedValue(self)
    }

    public func disabledValue() -> T? {
        return stateSelectorDisabledValue(self)
    }
}

func stateSelectorDefaultValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.default_
}

func stateSelectorHighlightedValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.highlighted ?? stateSelector.default_
}

func stateSelectorSelectedValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.selected ?? stateSelector.default_
}

func stateSelectorDisabledValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.disabled ?? stateSelector.default_
}
