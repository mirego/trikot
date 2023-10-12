import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct TextShowcaseView: RootViewModelView {
    typealias VM = TextShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<TextShowcaseViewModel>

    var viewModel: TextShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: TextShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 10) {
                    Group {
                        VMDText(viewModel.largeTitle)
                            .style(.largeTitle)

                        VMDText(viewModel.title1)
                            .style(.title1)

                        VMDText(viewModel.title1Bold)
                            .style(.title1)
                            .bold()

                        VMDText(viewModel.title2)
                            .style(.title2)

                        VMDText(viewModel.title2Bold)
                            .style(.title2)
                            .bold()

                        VMDText(viewModel.title3)
                            .style(.title3)
                    }

                    Group {
                        VMDText(viewModel.headline)
                            .style(.headline)

                        VMDText(viewModel.body)
                            .style(.body)

                        VMDText(viewModel.bodyMedium)
                            .style(.body)
                            .medium()

                        VMDText(viewModel.button)
                            .style(.button)

                        VMDText(viewModel.callout)
                            .style(.callout)

                        VMDText(viewModel.subheadline)
                            .style(.subheadline)

                        VMDText(viewModel.footnote)
                            .style(.footnote)

                        VMDText(viewModel.caption1)
                            .style(.caption1)

                        VMDText(viewModel.caption2)
                            .style(.caption2)
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

struct TextShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            TextShowcaseView(viewModel: TextShowcaseViewModelPreview())
        }
    }
}
