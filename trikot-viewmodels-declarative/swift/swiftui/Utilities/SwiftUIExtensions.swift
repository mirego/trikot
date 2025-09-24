import SwiftUI
import Jasper

extension ForEach where ID == String, Content : View, Data.Element : VMDIdentifiableContent {
    public init(_ data: Data, @ViewBuilder content: @escaping (Data.Element) -> Content) {
        self.init(data, id: \Data.Element.identifier, content: content)
    }
}
