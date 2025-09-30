//
//  LiveActivityHandler.swift
//  iosApp
//
//  Created by Tina on 9/17/25.
//

// In iosApp/iosApp/LiveActivityHandler.swift

import Foundation
import ActivityKit
import ComposeApp

public class LiveActivityManager: NSObject, TimerController {
    private var activity: Activity<TimerActivityAttributes>? = nil
    private var remainingSeconds: Int64? = nil  // Store remaining time when paused

    public func pause() {
        guard let activity = activity else { return }
        Task {
            // Calculate remaining seconds
            let targetDate = activity.content.state.targetDate
            let now = Date()
            let remaining = Int64(targetDate.timeIntervalSince(now))
            self.remainingSeconds = max(remaining, 0)

            // Freeze: update targetDate to now
            let frozenState = TimerActivityAttributes.ContentState(
                targetDate: now
            )
            let frozenContent = ActivityContent(state: frozenState, staleDate: nil)
            await activity.update(frozenContent)

            print("⏸ Live Activity paused with \(self.remainingSeconds ?? 0)s remaining")
        }
    }

    public func resume() {
        guard let activity = activity, let remaining = remainingSeconds else { return }
        Task {
            // Resume: recompute targetDate
            let resumedState = TimerActivityAttributes.ContentState(
                targetDate: Date().addingTimeInterval(TimeInterval(remaining))
            )
            let resumedContent = ActivityContent(state: resumedState, staleDate: nil)
            await activity.update(resumedContent)

            self.remainingSeconds = nil
            print("▶️ Live Activity resumed with \(remaining)s left")
        }
    }

    @objc public func start(durationInSeconds: Int64) {
        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
            print("Activities are not enabled.")
            return
        }

        let attributes = TimerActivityAttributes()
        let state = TimerActivityAttributes.ContentState(
            targetDate: Date().addingTimeInterval(TimeInterval(durationInSeconds))
        )
        let content = ActivityContent(state: state, staleDate: nil)

        do {
            activity = try Activity<TimerActivityAttributes>.request(
                attributes: attributes,
                content: content, // ✅ new API
                pushType: nil
            )
            print("✅ Live Activity started successfully.")
        } catch {
            print("❌ Error starting activity: \(error.localizedDescription)")
        }
    }

    @objc public func stop() {
        Task {
            if let activity = activity {
                await activity.end(
                    ActivityContent(state: activity.content.state, staleDate: nil),
                    dismissalPolicy: .immediate
                )
            }
        }
    }
}
