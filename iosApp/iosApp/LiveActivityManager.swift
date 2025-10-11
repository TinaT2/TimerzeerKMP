import Foundation
import ActivityKit
import ComposeApp
import KMPNativeCoroutinesAsync



public class LiveActivityManager: NSObject, @preconcurrency TimerController {
    
    private var activity: Activity<TimerActivityAttributes>? = nil
    private var remainingSeconds: Int64? = nil
    private var observeTask: Task<Void, Never>? = nil
    
    public override init() {
        super.init()
        observeTimerState()
    }
    
    // MARK: - Observe Kotlin TimerStatpae Flow
    private func observeTimerState() {
        observeTask?.cancel()
        observeTask = Task {
            
            let repository = KoinHelperKt.getTimerRepository()
            
            observeTask = Task {
                do {
                    // Convert the Kotlin StateFlow into a Swift AsyncSequence
                    let sequence = asyncSequence(for: repository.timerStateFlow)
                    
                    for try await state in sequence {
                        print("🕒 TimerState changed: running=\(state.isRunning), elapsed=\(state.elapsedTime)")
                        
                        // Example: Update Live Activity UI dynamically
                        if state.isRunning {
                            await self.updateActivity(elapsedTime: state.elapsedTime, mode: state.mode.name)
                        }
                    }
                } catch {
                    print("Error: \(error)")
                }
            }
        }
    }
    
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
            remainingSeconds = max(Int64(targetDate.timeIntervalSince(now)), 0)
            
            let frozenState = TimerActivityAttributes.ContentState(targetDate: now)
            let frozenContent = ActivityContent(state: frozenState, staleDate: nil)
            await activity.update(frozenContent)
            print("⏸ Paused with \(remainingSeconds ?? 0)s remaining")
        }
    }
    
    // MARK: - Resume
    public func resume() {
        guard let activity = activity, let remaining = remainingSeconds else { return }
        Task {
            let newTarget = Date().addingTimeInterval(TimeInterval(remaining))
            let newState = TimerActivityAttributes.ContentState(targetDate: newTarget)
            await activity.update(ActivityContent(state: newState, staleDate: nil))
            remainingSeconds = nil
            print("▶️ Resumed with \(remaining)s left")
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

               // COUNTDOWN → remaining time = elapsedTime (already decreasing)
               // STOPWATCH → elapsedTime increasing, so no targetDate
               let targetDate: Date
               if mode == "COUNTDOWN" {
                   targetDate = Date().addingTimeInterval(TimeInterval(elapsedTime) / 1000)
               } else {
                   targetDate = Date().addingTimeInterval(TimeInterval(elapsedTime) / 1000)
               }

               let newState = TimerActivityAttributes.ContentState(targetDate: targetDate)
               await activity.update(ActivityContent(state: newState, staleDate: nil))
               print("🔁 Updated Live Activity targetDate: \(targetDate)")
    }
}
