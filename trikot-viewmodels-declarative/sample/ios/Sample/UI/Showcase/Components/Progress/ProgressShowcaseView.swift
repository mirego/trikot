import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct ProgressShowcaseView: RootViewModelView {
    typealias VM = ProgressShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<ProgressShowcaseViewModel>

    var viewModel: ProgressShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: ProgressShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 40) {
                    ComponentShowcaseSectionView(viewModel.linearDeterminateProgressTitle) {
                        VMDProgressView(viewModel.determinateProgress)
                            .progressViewStyle(.linear)
                    }

                    ComponentShowcaseSectionView(viewModel.linearIndeterminateProgressTitle) {
                        VMDProgressView(viewModel.indeterminateProgress)
                            .progressViewStyle(.linearIndeterminate)
                    }

                    ComponentShowcaseSectionView(viewModel.circularDeterminateProgressTitle) {
                        VMDProgressView(viewModel.determinateProgress)
                            .progressViewStyle(.circularDeterminate)
                    }

                    ComponentShowcaseSectionView(viewModel.circularIndeterminateProgressTitle) {
                        VMDProgressView(viewModel.indeterminateProgress)
                            .progressViewStyle(.circular)
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
            }
            .navigationTitle(viewModel.title.text)
            .toolbar {
                VMDImageButton(viewModel.closeButton)
            }
        }
    }
}

struct ProgressShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ProgressShowcaseView(viewModel: ProgressShowcaseViewModelPreview())
        }
    }
}
