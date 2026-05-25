import SwiftUI
import Shared

struct LibraryView: View {
    var onShowClick: (Int32) -> Void
    @StateObject private var viewModel = IOSLibraryViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 10) {
                    ForEach(viewModel.categories, id: \.self) { category in
                        Button {
                            viewModel.selectedCategory = category
                            viewModel.loadCategory()
                        } label: {
                            Text(category)
                                .font(.subheadline.weight(.semibold))
                                .padding(.horizontal, 14)
                                .padding(.vertical, 8)
                                .background(
                                    viewModel.selectedCategory == category
                                    ? Color.accentColor
                                    : Color(.secondarySystemGroupedBackground)
                                )
                                .foregroundStyle(
                                    viewModel.selectedCategory == category ? .white : .primary
                                )
                                .clipShape(Capsule())
                        }
                    }
                }
                .padding()
            }

            List {
                if viewModel.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity)
                        .listRowBackground(Color.clear)
                } else if viewModel.shows.isEmpty {
                    IOSEmptyStateView(
                        title: "No shows",
                        systemImage: "rectangle.stack",
                        message: "No shows found in \(viewModel.selectedCategory)."
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
        }
        .navigationTitle("Library")
        .task {
            viewModel.loadCategory()
        }
    }
}
