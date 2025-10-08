//
//  TimerWidgetLiveActivity.swift
//  TimerWidget
//
//  Created by Tina on 9/16/25.

import ActivityKit
import WidgetKit
import SwiftUI

@main
struct TimerWidget: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: TimerActivityAttributes.self) { context in
            // MARK: Lock Screen UI
            VStack(spacing: 8) {
                Text("Countdown")
                    .font(.headline)
                Text(context.state.targetDate, style: .timer)
                    .font(.largeTitle.monospacedDigit())
            }
            .padding()

        } dynamicIsland: { context in

            // MARK: Dynamic Island UI
            DynamicIsland {
                // Expanded View
                DynamicIslandExpandedRegion(.center) {
                          VStack {
                              Text("Timer")
                              Text(context.state.targetDate, style: .timer)
                                  .font(.largeTitle.monospacedDigit())
                          }
                      }

            } compactLeading: {
                // Compact Leading
                Image(systemName: "timer")
                    .foregroundColor(.orange)

            } compactTrailing: {
                // Compact Trailing
                Text(context.state.targetDate, style: .timer)
                    .monospacedDigit()
                    .foregroundColor(.orange)

            } minimal: {
                // Minimal
                Image(systemName: "timer")
            }
        }
    }
}
