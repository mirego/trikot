import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct DialogView: RootViewModelView {
 
    @ObservedObject var observableViewModel: ObservableViewModelAdapter<DialogViewModel>

    var viewModel: DialogViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: DialogViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        VStack(alignment: .center, spacing: 32) {
            VMDText(viewModel.message)
            HStack(spacing: 16) {
                VMDButton(viewModel.leftButton) { content in
                    Text(content.text)
                }
                VMDButton(viewModel.rightButton) { content in
                    Text(content.text)
                }
            }
        }
        .padding(64)
        .background(
            RoundedRectangle(cornerRadius: 4)
                .fill(.white)
        )
    }
}
