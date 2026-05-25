import SwiftUI
import Shared

private enum IOSPreviewData {
    static let sampleShow = Show(
        id: 1,
        name: "The Swift Files",
        genres: ["Drama", "Science-Fiction"],
        language: "English",
        image: Image(medium: nil, original: nil),
        rating: Rating(average: 8.4)
    )
}

struct AboutView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            AboutView()
        }
    }
}

struct IOSShowCard_Previews: PreviewProvider {
    static var previews: some View {
        IOSShowCard(show: IOSPreviewData.sampleShow) {}
            .padding()
            .background(Color(.systemGroupedBackground))
            .previewLayout(.sizeThatFits)
    }
}

struct IOSEmptyStateView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            IOSEmptyStateView(
                title: "No saved shows",
                systemImage: "heart",
                message: "Tap Save on a show detail page to keep it here."
            )
            .previewDisplayName("Saved Empty")

            IOSEmptyStateView(
                title: "Search TV shows",
                systemImage: "magnifyingglass",
                message: "Type a show name to search TVMaze."
            )
            .previewDisplayName("Search Empty")
        }
        .padding()
    }
}
