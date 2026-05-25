import SwiftUI
import Shared

struct ExploreView: View {
    var onShowClick: (Int32) -> Void
    @StateObject private var viewModel = IOSExploreViewModel()
    
    var body: some View {
        ScrollView {
            LazyVStack(spacing: 14) {
                ForEach(viewModel.shows, id: \.id) { show in
                    IOSShowCard(show: show) {
                        onShowClick(show.id)
                    }
                    .onAppear {
                        if show.id == viewModel.shows.last?.id {
                            viewModel.loadNextPage()
                        }
                    }
                }

                if viewModel.isLoading {
                    ProgressView()
                        .padding()
                }

                if let errorMessage = viewModel.errorMessage {
                    Text(errorMessage)
                        .font(.footnote)
                        .foregroundStyle(.red)
                        .padding()
                }
            }
            .padding()
        }
        .background(Color(.systemGroupedBackground))
        .navigationTitle("Explore")
        .task {
            viewModel.loadInitialIfNeeded()
        }
    }
}
