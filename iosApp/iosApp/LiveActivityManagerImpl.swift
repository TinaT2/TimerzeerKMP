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

// This class must be public and inherit from NSObject to be visible to Kotlin
public class LiveActivityManagerImpl: NSObject, LiveActivityManager {

    private var activity: Activity<TimerActivityAttributes>? = nil

    // @objc makes this method callable from your KMM 'actual' class
    @objc public func start(durationInSeconds: Int64) {
        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
            print("Activities are not enabled.")
            return
        }

        let attributes = TimerActivityAttributes()
        let state = TimerActivityAttributes.ContentState(
            targetDate: Date().addingTimeInterval(TimeInterval(durationInSeconds))
        )

        do {
            activity = try Activity<TimerActivityAttributes>.request(
                attributes: attributes,
                contentState: state,
                pushType: nil
            )
            print("Live Activity started successfully.")
        } catch (let error) {
            print("Error starting activity: \(error.localizedDescription)")
        }
    }

    // @objc makes this method callable from your KMM 'actual' class
    @objc public func stop() {
        Task {
            // End the activity immediately
            await activity?.end(dismissalPolicy: .immediate)
            print("Live Activity stopped.")
            activity = nil // Clean up the reference
        }
    }
}
