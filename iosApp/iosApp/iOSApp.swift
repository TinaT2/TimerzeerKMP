import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        IosLiveActivityManager.shared.manager = LiveActivityManagerImpl()
       }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
