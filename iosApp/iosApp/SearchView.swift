import SwiftUI
import Shared

struct SearchView: View {
    var onShowClick: (Int32) -> Void
    @StateObject private var viewModel = IOSSearchViewModel()
    
    var body: some View {
        List {
            if viewModel.query.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
                IOSEmptyStateView(
                    title: "Search TV shows",
                    systemImage: "magnifyingglass",
                    message: "Type a show name to search TVMaze."
                )
                .listRowBackground(Color.clear)
            } else if viewModel.isLoading {
                ProgressView()
                    .frame(maxWidth: .infinity)
                    .listRowBackground(Color.clear)
            } else if viewModel.shows.isEmpty {
                IOSEmptyStateView(
                    title: "No results",
                    systemImage: "tv",
                    message: "Try another title."
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
        .navigationTitle("Search")
        .searchable(text: $viewModel.query, prompt: "Show name")
        .onSubmit(of: .search) {
            viewModel.search()
        }
        .onChange(of: viewModel.query) { _ in
            viewModel.search()
        }
    }
}
