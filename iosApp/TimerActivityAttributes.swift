//
//  TimerActivityAttributes.swift
//  iosApp
//
//  Created by Tina on 9/16/25.
//

import Foundation
import ActivityKit

struct TimerActivityAttributes: ActivityAttributes {
    // This struct defines the data that the widget will display.
    // It's the "state" of your Live Activity.
    public struct ContentState: Codable, Hashable {
        // The time when the timer is supposed to end.
        // We pass this once when we start the timer.
        var targetDate: Date
    }

    // You can add static configuration data here if needed.
    // For a simple timer, we don't need anything.
}
