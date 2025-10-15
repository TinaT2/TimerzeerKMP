import Foundation
import ActivityKit
import ComposeApp
import KMPNativeCoroutinesAsync



public class LiveActivityManager: NSObject, @preconcurrency TimerController {
    
    private var activity: Activity<TimerActivityAttributes>? = nil
    private var remainingSeconds: Int64? = nil
    private var observeTask: Task<Void, Never>? = nil
    
    // MARK: - Start
    @MainActor
    @objc public func start(durationInSeconds: Int64) {
        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
            print("Activities are not enabled.")
            return
        }
        
        Task{
            for existing in Activity<TimerActivityAttributes>.activities {
                await existing.end(
                    ActivityContent(state: existing.content.state, staleDate: nil),
                    dismissalPolicy: .immediate
                )
                print("🧹 Ended previous Live Activity.")
            }
            
            let attributes = TimerActivityAttributes()
            let state = TimerActivityAttributes.ContentState(
                targetDate: Date().addingTimeInterval(TimeInterval(durationInSeconds/1000))
            )
            let content = ActivityContent(state: state, staleDate: nil)
            
            do {
                activity = try Activity<TimerActivityAttributes>.request(
                    attributes: attributes,
                    content: content,
                    pushType: nil
                )
                print("✅ Live Activity started successfully.")
            } catch {
                print("❌ Error starting activity: \(error.localizedDescription)")
            }
        }
    }
    
    // MARK: - Pause
    public func pause() {
        guard let activity = activity else { return }
        Task {
            let now = Date()
            let targetDate = activity.content.state.targetDate
            remainingSeconds = Int64(targetDate.timeIntervalSince(now)*1000)
            
            await activity.end(
                ActivityContent(state: activity.content.state, staleDate: nil),
                dismissalPolicy: .immediate
            )
            
            print("⏸ Paused with \(remainingSeconds ?? 0)s remaining")
        }
    }
    
    // MARK: - Resume
    public func resume(durationInSeconds: Int64) {
        Task {
            let newTarget = Date().addingTimeInterval(TimeInterval(durationInSeconds)/1000)
            let newState = TimerActivityAttributes.ContentState(targetDate: newTarget)
            let content = ActivityContent(state: newState, staleDate: nil)
            let attributes = TimerActivityAttributes()
            
            do {
                activity = try Activity<TimerActivityAttributes>.request(
                    attributes: attributes,
                    content: content,
                    pushType: nil
                )
                print("✅ Live Activity started successfully.")
            } catch {
                print("❌ Error starting activity: \(error.localizedDescription)")
            }
            remainingSeconds = nil
            print("▶️ Resumed with \(durationInSeconds)s left")
        }
    }
    
    // MARK: - Stop
    @objc public func stop() {
        Task {
            if let activity = activity {
                await activity.end(
                    ActivityContent(state: activity.content.state, staleDate: nil),
                    dismissalPolicy: .immediate
                )
            }
            observeTask?.cancel()
        }
    }
    
    // MARK: - Update helper
    private func updateActivity(elapsedTime: Int64, mode: String) async {
        guard let activity = activity else { return }

               let targetDate = Date().addingTimeInterval(TimeInterval(elapsedTime) / 1000)
               

               let newState = TimerActivityAttributes.ContentState(targetDate: targetDate)
               await activity.update(ActivityContent(state: newState, staleDate: nil))
               print("🔁 Updated Live Activity targetDate: \(targetDate)")
    }
}
