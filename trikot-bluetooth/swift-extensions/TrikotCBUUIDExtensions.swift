import CoreBluetooth

extension CBUUID {
    func toPaddedUuidString() -> String {
        let returnValue = self.uuidString
        return returnValue.count == 4 ? "0000\(returnValue)-0000-1000-8000-00805F9B34FB" : returnValue
    }
}
