import Foundation
import TRIKOT_FRAMEWORK_NAME
import SwiftUI

class VMDPlatformNavigationControllerImpl: VMDPlatformNavigationController, ObservableObject{

    @Published var currentDestination: VMDNavigationDestination<AnyObject>?
    @Published var currentDestinationIdentifier: String? = GeneratedEnumOfDestinations.home.identifier
    
    @Published var currentModalDestination: VMDNavigationDestination<AnyObject>?
    @Published var currentModalIdentifier: String?
        
    func push(destination: VMDNavigationDestination<AnyObject>, options: VMDPushOptions?) {
        if (destination.type == VMDNavigationDestinationType.dialog) {
            currentModalDestination = destination
            currentModalIdentifier = destination.identifier
        } else{
            currentDestination = destination
            currentDestinationIdentifier = destination.identifier
        }
        
        objectWillChange.send()
    }
    
    func pop(destination: VMDNavigationDestination<AnyObject>, options: VMDPopOptions?) {
        if (currentModalDestination != nil){
            currentModalDestination = nil
            currentModalIdentifier = nil
        } else {
            currentDestination = destination
            currentDestinationIdentifier = destination.identifier
        }
        
        objectWillChange.send()
    }
    
}
