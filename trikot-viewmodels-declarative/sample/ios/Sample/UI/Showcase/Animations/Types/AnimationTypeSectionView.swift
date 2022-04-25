import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct AnimationTypeSectionView: ViewModelView {
    typealias VM = AnimationTypeShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<AnimationTypeShowcaseViewModel>

    var viewModel: AnimationTypeShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: AnimationTypeShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        VStack(alignment: viewModel.isTrailing ? .trailing : .leading, spacing: 10) {
            HStack {
                VMDText(viewModel.title)
                    .style(.title2)
                    .frame(maxWidth: .infinity, alignment: .leading)

                VMDButton(viewModel.animateButton) { Text($0.text) }
            }

            Circle()
                .foregroundColor(Color.red)
                .frame(width: 30, height: 30)
        }
    }
}
