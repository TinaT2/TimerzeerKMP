import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}

//import SwiftUI
//import ActivityKit // Don't forget this import!

//struct ContentView: View {
//    @State private var activity: Activity<TimerActivityAttributes>? = nil
//
//    var body: some View {
//        VStack(spacing: 20) {
//            Text("Live Activity Timer")
//                .font(.title)
//
//            Button("Start 30 Second Timer") {
//                startActivity()
//            }
//            .buttonStyle(.borderedProminent)
//
//            Button("Stop Timer") {
//                stopActivity()
//            }
//            .buttonStyle(.bordered)
//        }
//    }
//
//    func startActivity() {
//        // Make sure activities are enabled
//        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
//            print("Activities are not enabled.")
//            return
//        }
//
//        // Define the constant attributes and the initial state
//        let attributes = TimerActivityAttributes()
//        let state = TimerActivityAttributes.ContentState(targetDate: Date().addingTimeInterval(30)) // 30 seconds from now
//
//        // Start the activity
//        do {
//            activity = try Activity<TimerActivityAttributes>.request(
//                attributes: attributes,
//                contentState: state,
//                pushType: nil // No push notifications needed for this timer
//            )
//            print("Activity started successfully: \(activity?.id ?? "N/A")")
//        } catch (let error) {
//            print("Error starting activity: \(error.localizedDescription)")
//        }
//    }
//
//    func stopActivity() {
//        guard let activity = activity else { return }
//
//        Task {
//            // End the activity immediately
//            await activity.end(dismissalPolicy: .immediate)
//            print("Activity stopped.")
//        }
//    }
//}



