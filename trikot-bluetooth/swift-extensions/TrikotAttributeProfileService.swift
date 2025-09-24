import Jasper
import CoreBluetooth

class TrikotAttributeProfileService: NSObject, AttributeProfileService {
    private let service: CBService
    private let peripheral: CBPeripheral

    var trikotCharacteristics = [CBCharacteristic: TrikotAttributeProfileCharacteristic]()

    let characteristics: Publisher = Publishers().behaviorSubject(value: nil)

    init(service: CBService, peripheral: CBPeripheral) {
        self.service = service
        self.peripheral = peripheral
    }

    func updateCharacteristics() {
        service.characteristics?.forEach {
            if (trikotCharacteristics[$0] == nil) {
                trikotCharacteristics[$0] = TrikotAttributeProfileCharacteristic(characteristic: $0, peripheral: peripheral)
            }
        }

        let values = trikotCharacteristics.reduce([String: AttributeProfileCharacteristic](), { (result, value) -> [String: AttributeProfileCharacteristic] in
            var mutable = result
            mutable[value.key.uuid.toPaddedUuidString().uppercased()] = value.value
            return mutable
        })

        characteristics.asBehaviorSubject().value = values
    }
}
