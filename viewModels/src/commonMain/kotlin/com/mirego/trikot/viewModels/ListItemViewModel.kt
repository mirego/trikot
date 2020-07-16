package com.mirego.trikot.viewmodels

interface ListItemViewModel : ViewModel {
    var comparableId: String

    /**
     * Called to check whether two objects represent the same item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     */
    fun isTheSame(other: ListItemViewModel): Boolean {
        return comparableId == other.comparableId
    }

    /**
     * Called to check whether two items have the same items' visual representations.
     * <p>
     * This information is used to detect if the contents of an item have changed.
     * <p>
     * This method to check equality instead of {@link Object#equals(Object)} so that you can
     * change its behavior depending on your UI.
     */
    fun haveTheSameContent(other: ListItemViewModel): Boolean {
        return true
    }
}
