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

extension StateSelector where T == TrikotImageResource {
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
    return stateSelector.defaultState
}

func stateSelectorHighlightedValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.highlighted ?? stateSelector.defaultState
}

func stateSelectorSelectedValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.selected ?? stateSelector.defaultState
}

func stateSelectorDisabledValue<T>(_ stateSelector: StateSelector<T>) -> T? {
    return stateSelector.disabled ?? stateSelector.defaultState
}
