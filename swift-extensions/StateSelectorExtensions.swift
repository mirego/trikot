import TRIKOT_FRAMEWORK_NAME

extension StateSelector {
    public func defaultValue<T>() -> T? {
        return default_ as? T
    }

    public func highlightedValue<T>() -> T? {
        return highlighted as? T ?? defaultValue()
    }

    public func selectedValue<T>() -> T? {
        return selected as? T ?? defaultValue()
    }

    public func disabledValue<T>() -> T? {
        return disabled as? T ?? defaultValue()
    }
}
