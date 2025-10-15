//
//  TimerActivityAttributes.swift
//  iosApp
//
//  Created by Tina on 9/16/25.
//

import Foundation
import ActivityKit

struct TimerActivityAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        var targetDate: Date
        var isPaused: Bool = false
        var remainingMillis: Int64? = 0
    }
}
