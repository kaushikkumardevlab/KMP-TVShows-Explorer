import SwiftUI
import Shared

struct ShowDetailView: View {
    let showId: Int32
    @StateObject private var viewModel = IOSShowDetailViewModel()

    private var groupedEpisodes: [(season: Int32, episodes: [Episode])] {
        let grouped = Dictionary(grouping: viewModel.episodes) { $0.season }
        return grouped
            .map { (season: $0.key, episodes: $0.value.sorted { $0.number < $1.number }) }
            .sorted { $0.season < $1.season }
    }

    var body: some View {
        ScrollView {
            if viewModel.isLoading && viewModel.show == nil {
                ProgressView()
                    .padding(.top, 80)
            } else if let show = viewModel.show {
                VStack(alignment: .leading, spacing: 20) {
                    hero(show)
                    metadata(show)
                    summary(show)
                    episodes
                }
                .padding(.bottom, 32)
            } else if let errorMessage = viewModel.errorMessage {
                IOSEmptyStateView(
                    title: "Unable to load show",
                    systemImage: "exclamationmark.triangle",
                    message: errorMessage
                )
                .padding(.top, 80)
            }
        }
        .background(Color(.systemGroupedBackground))
        .navigationTitle(viewModel.show?.name ?? "Details")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    viewModel.toggleFavorite()
                } label: {
                    Image(systemName: viewModel.isFavorite ? "heart.fill" : "heart")
                }
                .disabled(viewModel.show == nil)
            }
        }
        .task {
            viewModel.load(showId: showId)
        }
    }

    private func hero(_ show: ShowDetail) -> some View {
        AsyncImage(url: URL(string: show.imageUrl ?? "")) { phase in
            switch phase {
            case .success(let image):
                image.resizable().scaledToFill()
            default:
                Rectangle().fill(Color.secondary.opacity(0.18))
            }
        }
        .frame(height: 360)
        .frame(maxWidth: .infinity)
        .clipped()
        .overlay(alignment: .bottomLeading) {
            VStack(alignment: .leading, spacing: 8) {
                Text(show.name)
                    .font(.largeTitle.weight(.bold))
                    .foregroundStyle(.white)
                    .shadow(radius: 8)
                Text(ratingText(show.rating))
                    .font(.headline)
                    .foregroundStyle(.yellow)
            }
            .padding()
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(
                LinearGradient(
                    colors: [.clear, .black.opacity(0.72)],
                    startPoint: .top,
                    endPoint: .bottom
                )
            )
        }
    }

    private func metadata(_ show: ShowDetail) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            if !show.genres.isEmpty {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack {
                        ForEach(show.genres, id: \.self) { genre in
                            Text(genre)
                                .font(.caption.weight(.semibold))
                                .padding(.horizontal, 12)
                                .padding(.vertical, 7)
                                .background(Color.accentColor.opacity(0.14))
                                .foregroundStyle(Color.accentColor)
                                .clipShape(Capsule())
                        }
                    }
                }
            }

            if let language = show.language {
                Label(language, systemImage: "globe")
                    .font(.subheadline)
                    .foregroundStyle(.secondary)
            }
        }
        .padding(.horizontal)
    }

    private func summary(_ show: ShowDetail) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Story")
                .font(.title3.weight(.bold))
            Text(show.summary ?? "No summary available.")
                .font(.body)
                .foregroundStyle(.secondary)
        }
        .padding(.horizontal)
    }

    private var episodes: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Episodes")
                .font(.title3.weight(.bold))
                .padding(.horizontal)

            if groupedEpisodes.isEmpty {
                Text("No episodes available.")
                    .foregroundStyle(.secondary)
                    .padding(.horizontal)
            } else {
                ForEach(groupedEpisodes, id: \.season) { group in
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Season \(group.season)")
                            .font(.headline)
                            .padding(.horizontal)

                        ForEach(group.episodes, id: \.id) { episode in
                            HStack {
                                Text("E\(episode.number)")
                                    .font(.caption.weight(.bold))
                                    .foregroundStyle(.secondary)
                                    .frame(width: 42, alignment: .leading)
                                Text(episode.name)
                                    .font(.subheadline)
                                Spacer()
                            }
                            .padding()
                            .background(.background)
                            .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                            .padding(.horizontal)
                        }
                    }
                }
            }
        }
    }
}
