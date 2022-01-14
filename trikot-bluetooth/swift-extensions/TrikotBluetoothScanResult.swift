import TRIKOT_FRAMEWORK_NAME
import CoreBluetooth

class TrikotBluetoothScanResult: NSObject, BluetoothScanResult {
    private let HeartbeatDuration: TimeInterval = 45
    private let centralManager: CBCentralManager
    private let peripheral: CBPeripheral
    private var lostHeartbeatTimer: Foundation.Timer?
    private var manufacturerData = frozenBehaviorSubject()

    var bluetoothDevice: TrikotBluetoothDevice?

    var name: String

    var physicalAddress: String

    var rssi: Publisher = frozenBehaviorSubject()

    var onHeartBeatLost: (() -> Void)? {
        didSet {
            lostHeartbeatTimer?.invalidate()
        }
    }

    init(centralManager: CBCentralManager, peripheral: CBPeripheral, advertisementData: [String : Any], rssi: NSNumber) {
        self.centralManager = centralManager
        self.peripheral = peripheral

        name = peripheral.name ?? ""
        physicalAddress = peripheral.identifier.uuidString
        super.init()
        updateWith(advertisementData: advertisementData, rssi: rssi)
    }

    deinit {
        lostHeartbeatTimer?.invalidate()
    }

    func manufacturerSpecificData(manufacturerId: Int32) -> Publisher {
        return PublisherExtensionsKt.map(manufacturerData) { data in
            guard let data = data as? Data else { return Data().toKotlinByteArray() }
            return data.dropFirst(2).toKotlinByteArray()
        }
    }

    func connect(cancellableManager: CancellableManager) -> BluetoothDevice {
        let bluetoothDevice = TrikotBluetoothDevice(centralManager: centralManager, peripheral: peripheral)
        self.bluetoothDevice = bluetoothDevice
        bluetoothDevice.connect(cancellableManager: cancellableManager)
        return bluetoothDevice
    }

    func updateWith(advertisementData: [String : Any], rssi: NSNumber) {
        updateManufacturerData(advertisementData)
        self.rssi.asBehaviorSubject().value = rssi.int32Value
        doHeartbeat()
    }

    private func updateManufacturerData(_ advertisementData: [String : Any]) {
        guard let data = advertisementData[CBAdvertisementDataManufacturerDataKey] as? Data else { return }
        manufacturerData.asBehaviorSubject().value = data
    }

    func doHeartbeat() {
        DispatchQueue.main.async {
            self.lostHeartbeatTimer?.invalidate()
            self.lostHeartbeatTimer = Foundation.Timer.scheduledTimer(withTimeInterval: self.HeartbeatDuration, repeats: false, block: {[weak self] (_) in
                self?.onHeartBeatLost?()
            })
        }
    }

    override func isEqual(_ object: Any?) -> Bool {
        if let scanResult = object as? TrikotBluetoothScanResult {
            return scanResult.physicalAddress == physicalAddress
        }
        return false
    }
}
