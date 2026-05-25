import SwiftUI
import Shared

struct SavedView: View {
    var onShowClick: (Int32) -> Void
    @StateObject private var viewModel = IOSSavedViewModel()
    
    var body: some View {
        List {
            if viewModel.isLoading {
                ProgressView()
                    .frame(maxWidth: .infinity)
                    .listRowBackground(Color.clear)
            } else if viewModel.shows.isEmpty {
                IOSEmptyStateView(
                    title: "No saved shows",
                    systemImage: "heart",
                    message: "Tap Save on a show detail page to keep it here."
                )
                .listRowBackground(Color.clear)
            } else {
                ForEach(viewModel.shows, id: \.id) { show in
                    IOSShowCard(show: show) {
                        onShowClick(show.id)
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                }
            }
        }
        .listStyle(.plain)
        .navigationTitle("Saved")
        .onAppear {
            viewModel.loadFavorites()
        }
    }
}
