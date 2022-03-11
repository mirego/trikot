import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct AnimationTypesShowcaseView: RootViewModelView {
    typealias VM = AnimationTypesShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<AnimationTypesShowcaseViewModel>

    var viewModel: AnimationTypesShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: AnimationTypesShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 40) {
                    animationSection(
                        title: viewModel.linearTitle,
                        button: viewModel.linearAnimateButton,
                        isTrailing: viewModel.linearIsTrailing
                    )

                    animationSection(
                        title: viewModel.easeInTitle,
                        button: viewModel.easeInAnimateButton,
                        isTrailing: viewModel.easeInIsTrailing
                    )

                    animationSection(
                        title: viewModel.easeOutTitle,
                        button: viewModel.easeOutAnimateButton,
                        isTrailing: viewModel.easeOutIsTrailing
                    )

                    animationSection(
                        title: viewModel.easeInEaseOutTitle,
                        button: viewModel.easeInEaseOutAnimateButton,
                        isTrailing: viewModel.easeInEaseOutIsTrailing
                    )

                    animationSection(
                        title: viewModel.cubicBezierTitle,
                        button: viewModel.cubicBezierAnimateButton,
                        isTrailing: viewModel.cubicBezierIsTrailing
                    )

                    animationSection(
                        title: viewModel.springTitle,
                        button: viewModel.springAnimateButton,
                        isTrailing: viewModel.springIsTrailing
                    )
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

    private func animationSection(title: VMDTextViewModel, button: VMDButtonViewModel<VMDTextContent>, isTrailing: Bool) -> some View {
        VStack(alignment: isTrailing ? .trailing : .leading, spacing: 10) {
            HStack {
                VMDText(title)
                    .style(.title2)
                    .frame(maxWidth: .infinity, alignment: .leading)

                VMDButton(button) { Text($0.text) }
            }

            animatedView()
        }
    }

    private func animatedView() -> some View {
        Circle()
            .foregroundColor(Color.red)
            .frame(width: 30, height: 30)
    }
}

struct AnimationTypesShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            AnimationTypesShowcaseView(viewModel: AnimationTypesShowcaseViewModelPreview())
        }
    }
}
