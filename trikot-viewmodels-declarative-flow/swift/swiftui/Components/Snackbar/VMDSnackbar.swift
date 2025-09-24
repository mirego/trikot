import SwiftUI
import Jasper

public struct VMDSnackbar<Content>: View where Content: View {
    private let viewDataFlow: VMDFlow<VMDSnackbarViewData>
    private let snackbarBuilder: (VMDSnackBarStateObject) -> Content
    @StateObject private var snackBarStateObject = VMDSnackBarStateObject()
    
    public init(_ viewDataFlow: VMDFlow<VMDSnackbarViewData>, @ViewBuilder content: @escaping (VMDSnackBarStateObject) -> Content) {
        self.viewDataFlow = viewDataFlow
        snackbarBuilder = content
    }
    
    public var body: some View {
        snackbarBuilder(snackBarStateObject)
            .onAppear {
                snackBarStateObject.startWatching(viewDataFlow)
            }
    }
}
