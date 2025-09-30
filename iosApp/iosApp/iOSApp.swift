import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        InitKoinKt.startKoinForiOS()
        IosLiveActivityManager.shared.manager = LiveActivityManager()
       }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

