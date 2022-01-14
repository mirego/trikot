import TRIKOT_FRAMEWORK_NAME
import CoreBluetooth
import Dispatch

public class TrikotBluetoothManager: NSObject, BluetoothManager, CBCentralManagerDelegate {
    private static let QueueLabel = "bluetooth-serial"
    private let dispatchQueue = DispatchQueue(label: TrikotBluetoothManager.QueueLabel, qos: .background)
    private lazy var centralManager = CBCentralManager(delegate: self, queue: dispatchQueue)

    private let scanResultpublisher = frozenBehaviorSubject()
    private let numberOfScanRequest = Atomic<Int>(0)

    private var scanResults = [CBPeripheral: TrikotBluetoothScanResult]()
    private var connectedDevices = [CBPeripheral: TrikotBluetoothDevice]()
    private var delegates = [CBCentralManagerDelegate]()

    private let bluetoothStatePublisher: Publisher = frozenBehaviorSubject()

    public lazy var statePublisher = PublisherExtensionsKt.map(bluetoothStatePublisher) { (value: Any) in
        guard let centralManagerState = value as? CBManagerState else { return nil }

        switch centralManagerState {
        case .unknown, .resetting, .unsupported, .unauthorized, .poweredOff:
            return BluetoothManagerState.off
        case .poweredOn:
            return BluetoothManagerState.on
        default:
            return BluetoothManagerState.off
        }
    }

    public lazy var missingPermissionsPublisher = PublisherExtensionsKt.map(bluetoothStatePublisher) { (value: Any) in
        guard let centralManagerState = value as? CBManagerState else { return nil }
        return centralManagerState != .unauthorized ? [] : [BluetoothManagerPermission.bluetooth]
    }

    func refreshLocationPermission() {
    }

    private var centralManagerState: CBManagerState = .poweredOff {
        didSet {
            bluetoothStatePublisher.asBehaviorSubject().value = centralManagerState
            if (centralManager.state == .poweredOff) {
                connectedDevices.forEach { (key: CBPeripheral, value: TrikotBluetoothDevice) in
                    value.managerIsConnected = false
                }
                connectedDevices.removeAll()
            }
        }
    }

    public override init() {
        super.init()
        centralManagerState = centralManager.state
    }

    public func scanForDevices(cancellableManager: CancellableManager, serviceUUIDs: [String]) -> Publisher {
        numberOfScanRequest.mutate {[weak self] in
            $0 += 1
            if ($0 == 1) {
                dispatchQueue.async {
                    self?.clearScanResult()
                    self?.centralManager.scanForPeripherals(withServices: serviceUUIDs.map { CBUUID(string:$0) }, options: [CBCentralManagerScanOptionAllowDuplicatesKey: true])
                }
            }
        }

        cancellableManager.add {[weak self] in
            self?.numberOfScanRequest.mutate {[weak self] in
                $0 -= 1
                if ($0 == 0) {
                    self?.stopScan()
                }
            }
        }

        return scanResultpublisher
    }

    func stopScan() {
        if centralManagerState == .poweredOn {
            centralManager.stopScan()
        }
    }

    public func centralManagerDidUpdateState(_ central: CBCentralManager) {
        centralManagerState = centralManager.state
    }

    public func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral, advertisementData: [String : Any], rssi RSSI: NSNumber) {
        if (scanResults[peripheral] == nil) {
            let trikotBluetoothScanResult = TrikotBluetoothScanResult(centralManager: centralManager, peripheral: peripheral, advertisementData: advertisementData, rssi: RSSI)
            trikotBluetoothScanResult.onHeartBeatLost = {[weak self] in
                self?.dispatchQueue.async {
                    self?.scanResults.removeValue(forKey: peripheral)
                    self?.dispatchScanResult()
                }
            }
            scanResults[peripheral] = trikotBluetoothScanResult
            dispatchScanResult()
        } else {
            scanResults[peripheral]?.updateWith(advertisementData: advertisementData, rssi: RSSI)
        }
    }

    public func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral) {
        guard let bluetoothDevice = scanResults[peripheral]?.bluetoothDevice else { return }
        bluetoothDevice.managerIsConnected = true
        connectedDevices[peripheral] = bluetoothDevice
    }

    public func centralManager(_ central: CBCentralManager, didDisconnectPeripheral peripheral: CBPeripheral, error: Error?) {
        connectedDevices[peripheral]?.managerIsConnected = false
        connectedDevices[peripheral] = nil
    }

    public func centralManager(_ central: CBCentralManager, didFailToConnect peripheral: CBPeripheral, error: Error?) {
        connectedDevices[peripheral]?.managerIsConnected = false
        connectedDevices[peripheral] = nil
    }

    private func clearScanResult() {
        scanResults.forEach { (key, value) in
            value.onHeartBeatLost = nil
        }
        scanResults.removeAll()
        dispatchScanResult()
    }

    private func dispatchScanResult() {
        let values = scanResults.values.map { $0 }
        Foundation.DispatchQueue.main.async {[weak self] in
            self?.scanResultpublisher.asBehaviorSubject().value = values
        }
    }
}
