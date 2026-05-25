import SwiftUI
import Shared

@MainActor
final class IOSExploreViewModel: ObservableObject {
    @Published var shows: [Show] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let helper = KoinHelper()
    private var page: Int32 = 0
    private var endReached = false

    func loadInitialIfNeeded() {
        guard shows.isEmpty else { return }
        loadNextPage()
    }

    func loadNextPage() {
        guard !isLoading && !endReached else { return }
        isLoading = true
        errorMessage = nil

        helper.loadShows(page: page) { [weak self] result in
            DispatchQueue.main.async {
                guard let self else { return }
                let newShows = result
                self.endReached = newShows.isEmpty
                self.page += 1
                self.shows.append(contentsOf: newShows.filter { newShow in
                    !self.shows.contains { $0.id == newShow.id }
                })
                self.isLoading = false
            }
        } onError: { [weak self] message in
            DispatchQueue.main.async {
                self?.errorMessage = message
                self?.isLoading = false
            }
        }
    }
}

@MainActor
final class IOSSearchViewModel: ObservableObject {
    @Published var query = ""
    @Published var shows: [Show] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let helper = KoinHelper()

    func search() {
        let trimmedQuery = query.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmedQuery.isEmpty else {
            shows = []
            errorMessage = nil
            return
        }

        isLoading = true
        errorMessage = nil
        helper.searchShows(query: trimmedQuery) { [weak self] result in
            DispatchQueue.main.async {
                self?.shows = result
                self?.isLoading = false
            }
        } onError: { [weak self] message in
            DispatchQueue.main.async {
                self?.errorMessage = message
                self?.isLoading = false
            }
        }
    }
}

@MainActor
final class IOSLibraryViewModel: ObservableObject {
    @Published var selectedCategory = "Drama"
    @Published var shows: [Show] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    let categories = ["Drama", "Comedy", "Action", "Science-Fiction", "Romance", "Crime"]
    private let helper = KoinHelper()

    func loadCategory() {
        isLoading = true
        errorMessage = nil
        helper.loadShowsByCategory(category: selectedCategory) { [weak self] result in
            DispatchQueue.main.async {
                self?.shows = result
                self?.isLoading = false
            }
        } onError: { [weak self] message in
            DispatchQueue.main.async {
                self?.errorMessage = message
                self?.isLoading = false
            }
        }
    }
}

@MainActor
final class IOSSavedViewModel: ObservableObject {
    @Published var shows: [Show] = []
    @Published var isLoading = false

    private let helper = KoinHelper()

    func loadFavorites() {
        isLoading = true
        helper.loadFavoriteShows { [weak self] result in
            DispatchQueue.main.async {
                self?.shows = result
                self?.isLoading = false
            }
        }
    }
}

@MainActor
final class IOSShowDetailViewModel: ObservableObject {
    @Published var show: ShowDetail?
    @Published var episodes: [Episode] = []
    @Published var isFavorite = false
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let helper = KoinHelper()

    func load(showId: Int32) {
        isLoading = true
        errorMessage = nil

        helper.loadShowDetail(showId: showId) { [weak self] detail in
            DispatchQueue.main.async {
                self?.show = detail
                self?.isLoading = false
            }
        } onError: { [weak self] message in
            DispatchQueue.main.async {
                self?.errorMessage = message
                self?.isLoading = false
            }
        }

        helper.loadEpisodes(showId: showId) { [weak self] result in
            DispatchQueue.main.async {
                self?.episodes = result
            }
        } onError: { _ in }

        helper.isFavoriteShow(showId: showId) { [weak self] favorite in
            DispatchQueue.main.async {
                self?.isFavorite = favorite.boolValue
            }
        }
    }

    func toggleFavorite() {
        guard let show else { return }
        helper.toggleFavoriteShowDetail(showDetail: show) { [weak self] favorite in
            DispatchQueue.main.async {
                self?.isFavorite = favorite.boolValue
            }
        }
    }
}

struct IOSShowCard: View {
    let show: Show
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack(spacing: 14) {
                AsyncImage(url: URL(string: show.image?.medium ?? "")) { phase in
                    switch phase {
                    case .success(let image):
                        image.resizable().scaledToFill()
                    default:
                        Rectangle().fill(Color.secondary.opacity(0.18))
                    }
                }
                .frame(width: 74, height: 104)
                .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))

                VStack(alignment: .leading, spacing: 8) {
                    Text(show.name)
                        .font(.headline)
                        .foregroundStyle(.primary)
                        .multilineTextAlignment(.leading)

                    if let language = show.language {
                        Text(language)
                            .font(.subheadline)
                            .foregroundStyle(.secondary)
                    }

                    if !show.genres.isEmpty {
                        Text(show.genres.joined(separator: " • "))
                            .font(.caption)
                            .foregroundStyle(.secondary)
                            .lineLimit(2)
                    }

                    Text(ratingText(show.rating?.average))
                        .font(.caption.weight(.semibold))
                        .foregroundStyle(.yellow)
                }
                Spacer(minLength: 0)
            }
            .padding(12)
            .background(.background)
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
            .shadow(color: .black.opacity(0.08), radius: 10, x: 0, y: 4)
        }
        .buttonStyle(.plain)
    }
}

struct IOSEmptyStateView: View {
    let title: String
    let systemImage: String
    let message: String

    var body: some View {
        VStack(spacing: 12) {
            Image(systemName: systemImage)
                .font(.system(size: 42, weight: .semibold))
                .foregroundStyle(.secondary)
            Text(title)
                .font(.headline)
            Text(message)
                .font(.subheadline)
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 48)
        .padding(.horizontal, 24)
    }
}

func ratingText(_ value: Any?) -> String {
    if let value = value as? KotlinDouble {
        return String(format: "%.1f", value.doubleValue)
    }
    if let value = value as? Double {
        return String(format: "%.1f", value)
    }
    return "No rating"
}
