import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct PickerShowcaseView: RootViewModelView {
    typealias VM = PickerShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<PickerShowcaseViewModel>

    var viewModel: PickerShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: PickerShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 10) {
                    VMDText(viewModel.textPickerTitle)
                    VMDPicker(viewModel.textPicker, label: viewModel.textPickerTitle) { content in
                        Text(content.text)
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
            }
            .navigationTitle(viewModel.title.text)
            .toolbar {
                VMDButton(viewModel.closeButton) { (content: VMDImageContent) in
                    content.image.image
                }
            }
        }
    }
}
