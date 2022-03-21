import TRIKOT_FRAMEWORK_NAME
import CoreBluetooth

class TrikotBluetoothDevice: NSObject, BluetoothDevice, CBPeripheralDelegate {
    private let centralManager: CBCentralManager
    private let peripheral: CBPeripheral
    private var services = [CBService: TrikotAttributeProfileService]()

    var name: String

    var physicalAddress: String

    var attributeProfileServices: Publisher = frozenBehaviorSubject()
    var isConnected: Publisher = frozenBehaviorSubject()

    var managerIsConnected = false {
        didSet {
            if managerIsConnected {
                peripheral.discoverServices(nil)
            }
            isConnected.asBehaviorSubject().value = managerIsConnected
        }
    }

    init(centralManager: CBCentralManager, peripheral: CBPeripheral) {
        self.centralManager = centralManager
        self.peripheral = peripheral
        name = peripheral.name ?? ""
        physicalAddress = peripheral.identifier.uuidString
        super.init()

        peripheral.delegate = self
    }

    func connect(cancellableManager: CancellableManager) {
        centralManager.connect(peripheral, options: nil)
        cancellableManager.add {[weak self] in
            self?.centralManager.cancelPeripheralConnection(self!.peripheral)
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?) {
        if let error = error {
            print("Error discovering services for \(peripheral): \(error)")
        }

        peripheral.services?.forEach {
            if services[$0] == nil {
                peripheral.discoverCharacteristics(nil, for: $0)
                services[$0] = TrikotAttributeProfileService(service: $0, peripheral: peripheral)
            }
        }

        attributeProfileServices.asBehaviorSubject().value = services.reduce([String: AttributeProfileService](), { (result, value) -> [String: AttributeProfileService] in
            var mutable = result
            mutable[value.key.uuid.toPaddedUuidString().uppercased()] = value.value
            return mutable
        })
    }

    func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
        if let error = error {
            print("Error discovering characteristics for \(service): \(error)")
        }
        services[service]?.updateCharacteristics()
    }

    func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?) {
        guard let service = characteristic.service else { return }

        if let error = error {
            services[service]?.trikotCharacteristics[characteristic]?.newError = error
        } else {
            services[service]?.trikotCharacteristics[characteristic]?.newValue = characteristic.value
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didWriteValueFor characteristic: CBCharacteristic, error: Error?) {
        guard let service = characteristic.service else { return }

        if let error = error {
            services[service]?.trikotCharacteristics[characteristic]?.newError = error
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didUpdateNotificationStateFor characteristic: CBCharacteristic, error: Error?) {
        guard let service = characteristic.service else { return }

        if let error = error {
            services[service]?.trikotCharacteristics[characteristic]?.newError = error
        }
    }
}
