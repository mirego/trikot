import SwiftUI
import Trikot
import Jasper

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
                    AnimationTypeSectionView(viewModel: viewModel.linear)
                    AnimationTypeSectionView(viewModel: viewModel.easeIn)
                    AnimationTypeSectionView(viewModel: viewModel.easeOut)
                    AnimationTypeSectionView(viewModel: viewModel.easeInEaseOut)
                    AnimationTypeSectionView(viewModel: viewModel.cubicBezier)
                    AnimationTypeSectionView(viewModel: viewModel.spring)
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

struct AnimationTypesShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            AnimationTypesShowcaseView(viewModel: AnimationTypesShowcaseViewModelPreview())
        }
    }
}
