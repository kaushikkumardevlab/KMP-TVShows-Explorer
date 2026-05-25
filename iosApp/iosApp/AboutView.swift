import SwiftUI
import Shared

struct AboutView: View {
    var body: some View {
        Form {
            Section {
                VStack(alignment: .leading, spacing: 8) {
                    Text("KMP TV Explorer")
                        .font(.title2.weight(.bold))
                    Text("A Kotlin Multiplatform app with native SwiftUI presentation on iOS.")
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                }
                .padding(.vertical, 6)
            }

            Section("Showcase") {
                Label("SwiftUI tabs, lists, search, detail, and settings", systemImage: "swift")
                Label("Shared Ktor, SQLDelight, repositories, and use cases", systemImage: "square.stack.3d.up")
                Label("Favorites persist through shared local storage", systemImage: "heart")
            }

            Section("Data") {
                Text("TV show data provided by TVMaze API.")
            }
        }
        .navigationTitle("About")
    }
}
