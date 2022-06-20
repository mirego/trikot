import Introspect
import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDTextField<Label>: View where Label: View {
    private let labelBuilder: (() -> Label)?
    private let onFocusChange: ((Bool) -> Void)?

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDTextFieldViewModel>

    private var viewModel: VMDTextFieldViewModel {
        observableViewModel.viewModel
    }

    @State private var text: String = ""

    private var prompt: Text? {
        if !viewModel.placeholder.isEmpty {
            return Text(viewModel.placeholder)
        } else {
            return nil
        }
    }

    @available(iOS 15, *)
    @State var isFocused = false

    public init(_ viewModel: VMDTextFieldViewModel, onFocusChange: ((Bool) -> Void)? = nil) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.onFocusChange = onFocusChange
    }

    public init(_ viewModel: VMDTextFieldViewModel, onFocusChange: ((Bool) -> Void)? = nil, @ViewBuilder label: @escaping () -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
        self.onFocusChange = onFocusChange
    }

    public var body: some View {
        if #available(iOS 15.0, *) {
            // Workaround to avoid a crash on iOS 14 in Release. Ref: https://github.com/gongzhang/swiftui-availability-check-crash
            Group {
                if #available(iOS 15.0, *) {
                    if let labelBuilder = labelBuilder {
                        TextField(text: $text, prompt: prompt, label: labelBuilder)
                            .onSubmit {
                                viewModel.onReturnKeyTap()
                            }
                            .focusMe($isFocused)
                            .onChange(of: isFocused) { isFocused in
                                self.onFocusChange?(isFocused)
                            }
                            .keyboardType(viewModel.keyboardType.uiKeyboardType)
                            .submitLabel(viewModel.keyboardReturnKeyType.submitLabel)
                            .textContentType(viewModel.contentType?.uiTextContentType)
                            .autocapitalization(viewModel.autoCapitalization.uiTextAutocapitalizationType)
                            .disableAutocorrection(!viewModel.autoCorrect)
                            .hidden(viewModel.isHidden)
                    } else {
                        TextField(viewModel.placeholder, text: $text, prompt: prompt)
                            .onSubmit {
                                viewModel.onReturnKeyTap()
                            }
                            .focusMe($isFocused)
                            .onChange(of: isFocused) { isFocused in
                                self.onFocusChange?(isFocused)
                            }
                            .keyboardType(viewModel.keyboardType.uiKeyboardType)
                            .submitLabel(viewModel.keyboardReturnKeyType.submitLabel)
                            .textContentType(viewModel.contentType?.uiTextContentType)
                            .autocapitalization(viewModel.autoCapitalization.uiTextAutocapitalizationType)
                            .disableAutocorrection(!viewModel.autoCorrect)
                            .hidden(viewModel.isHidden)
                    }
                }
            }
            .onChange(of: viewModel.text) { newValue in
                text = viewModel.formatText(newValue)
            }
            .onChange(of: text) { newValue in
                handleTextTransformations(newValue)
            }
        } else {
            TextField(viewModel.placeholder, text: $text, onEditingChanged: { isEditing in
                self.onFocusChange?(isEditing)
            }, onCommit: {
                viewModel.onReturnKeyTap()
            })
                .keyboardType(viewModel.keyboardType.uiKeyboardType)
                .introspectTextField { textfield in
                    textfield.returnKeyType = viewModel.keyboardReturnKeyType.uiReturnKeyType
                }
                .textContentType(viewModel.contentType?.uiTextContentType)
                .autocapitalization(viewModel.autoCapitalization.uiTextAutocapitalizationType)
                .disableAutocorrection(!viewModel.autoCorrect)
                .onChange(of: viewModel.text) { newValue in
                    text = viewModel.formatText(newValue)
                }
                .onChange(of: text) { newValue in
                    handleTextTransformations(newValue)
                }
        }
    }

    private func handleTextTransformations(_ value: String) {
        let unformattedText = viewModel.unformatText(value)
        text = viewModel.formatText(viewModel.transformText(unformattedText))
        viewModel.onValueChange(text: unformattedText)
    }
}
