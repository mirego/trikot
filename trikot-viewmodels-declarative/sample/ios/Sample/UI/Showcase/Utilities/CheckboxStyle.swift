import SwiftUI

struct CheckboxStyle: ToggleStyle {
    private let includeLabel: Bool

    init(includeLabel: Bool) {
        self.includeLabel = includeLabel
    }

    func makeBody(configuration: Configuration) -> some View {
        if includeLabel {
            HStack {
                configuration.label

                Spacer()

                checkbox(configuration: configuration)
            }
        } else {
            checkbox(configuration: configuration)
        }
    }

    private func checkbox(configuration: Configuration) -> some View {
        Image(systemName: configuration.isOn ? "checkmark.square.fill" : "square")
            .resizable()
            .frame(width: 24, height: 24)
            .foregroundColor(configuration.isOn ? .purple : .gray)
            .font(.system(size: 20, weight: .bold, design: .default))
            .onTapGesture {
                configuration.isOn.toggle()
            }
    }
}

extension ToggleStyle where Self == CheckboxStyle {
    static var squareCheckbox: CheckboxStyle {
        get {
            CheckboxStyle(includeLabel: false)
        }
    }

    static var squareCheckboxWithLabel: CheckboxStyle {
        get {
            CheckboxStyle(includeLabel: true)
        }
    }
}
