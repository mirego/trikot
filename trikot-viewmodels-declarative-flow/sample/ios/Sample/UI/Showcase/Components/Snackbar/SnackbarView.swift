import SwiftUI
import Trikot
import Jasper

struct SnackbarView: View {

    private let flow: VMDFlow<VMDSnackbarViewData>
    private let height: CGFloat
    private static let iPadMaxWidth: CGFloat = 480

    init(flow: VMDFlow<VMDSnackbarViewData>, height: CGFloat = 70) {
        self.flow = flow
        self.height = height
    }

    var body: some View {
        VMDSnackbar(flow) { snackBarStateObject in
            ZStack(alignment: .center) {
                Color.gray
                    .frame(height: height, alignment: .center)
                    .cornerRadius(10)
                    .padding(.horizontal, 10)

                HStack {
                    Image(uiImage: UIImage(named: "warning_icon") ?? UIImage())
                        .frame(width: 24, height: 24, alignment: .center)
                        .foregroundColor(Color.white)
                        .padding(.leading, 5)
                        .padding(.trailing, 5)

                    Text(snackBarStateObject.message)
                        .font(.footnote)
                        .foregroundColor(.white)
                        .multilineTextAlignment(.leading)
                        .padding(.trailing, 20)
                }
                .frame(maxWidth: SnackbarView.iPadMaxWidth, alignment: .leading)
                .padding(.horizontal, 20)

                if (snackBarStateObject.withDismissAction) {
                    VStack {
                        HStack {
                            Spacer()
                            Button {
                                snackBarStateObject.isVisible = false
                            } label: {
                                Image(systemName: "xmark")
                                    .foregroundColor(.white)
                                    .font(.system(size: 15))
                                    .padding(.trailing, 15)
                                    .padding(.top, 9)
                            }
                        }
                        Spacer()
                    }
                    .frame(height: height)
                }
            }
            .frame(maxWidth: SnackbarView.iPadMaxWidth, alignment: .leading)
            .opacity(snackBarStateObject.isVisible ? 1 : 0)
            .offset(y: snackBarStateObject.isVisible ? 0 : 15)
            .animation(.easeInOut, value: snackBarStateObject.isVisible)
        }
    }
}
