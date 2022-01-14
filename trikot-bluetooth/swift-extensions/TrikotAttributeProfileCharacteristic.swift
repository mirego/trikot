import TRIKOT_FRAMEWORK_NAME
import CoreBluetooth

class TrikotAttributeProfileCharacteristic: NSObject, AttributeProfileCharacteristic {
    private let characteristic: CBCharacteristic
    private let peripheral: CBPeripheral
    let event = frozenSubject()

    var newValue: Data? {
        didSet {
            guard let newValue = newValue else { return }
            if newValue.count > 0 {
                let byteArray = newValue.toKotlinByteArray()
                event.asSubject().value = AttributeProfileCharacteristicEvent(value: byteArray, error: nil)
            }
        }
    }

    var newError: Error? {
        didSet {
            guard let newError = newError else { return }
            event.asSubject().value = AttributeProfileCharacteristicEvent(value: nil,
                                                                          error: BluetoothCharacteristicException(cause: newError.localizedDescription))
        }
    }

    let uuid: String

    func read() {
        peripheral.readValue(for: characteristic)
    }

    func watch() {
        self.peripheral.setNotifyValue(true, for: self.characteristic)
    }

    func watchWithIndication() {
        self.peripheral.setNotifyValue(true, for: self.characteristic)
    }

    func write(data: KotlinByteArray) {
        peripheral.writeValue(ByteArrayNativeUtils().convert(byteArray: data), for: characteristic, type: .withResponse)
    }

    init(characteristic: CBCharacteristic, peripheral: CBPeripheral) {
        self.characteristic = characteristic
        uuid = characteristic.uuid.toPaddedUuidString()
        self.peripheral = peripheral
        super.init()        
    }
}

