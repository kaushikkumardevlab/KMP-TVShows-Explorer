import UIKit
import SwiftUI
import Shared

class ThemeManager: ObservableObject {
    @Published var colorScheme: ColorScheme? = nil
    
    init() {
        KoinHelper().observeTheme { isDark in
            DispatchQueue.main.async {
                if let isDark = isDark {
                    self.colorScheme = isDark.boolValue ? .dark : .light
                } else {
                    self.colorScheme = nil // Follow system
                }
            }
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    let type: ScreenType
    var showId: Int32 = 0
    var onShowClick: ((Int32) -> Void)? = nil
    var onBackClick: (() -> Void)? = nil

    enum ScreenType {
        case home, search, categories, favorites, about, detail
    }

    func makeUIViewController(context: Context) -> UIViewController {
        let factory = KoinHelper()
        switch type {
        case .home:
            return factory.createHomeViewController(onShowClick: { id in
                onShowClick?(Int32(truncating: id))
            })
        case .search:
            return factory.createSearchViewController(onShowClick: { id in
                onShowClick?(Int32(truncating: id))
            })
        case .categories:
            return factory.createCategoriesViewController(onShowClick: { id in
                onShowClick?(Int32(truncating: id))
            })
        case .favorites:
            return factory.createFavoritesViewController(onShowClick: { id in
                onShowClick?(Int32(truncating: id))
            })
        case .about:
            return factory.createAboutViewController()
        case .detail:
            return factory.createDetailViewController(showId: showId, onBackClick: {
                onBackClick?()
            })
        }
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @State private var path = NavigationPath()
    @StateObject private var themeManager = ThemeManager()

    var body: some View {
        NavigationStack(path: $path) {
            TabView {
                ExploreView(onShowClick: { id in
                    path.append(id)
                })
                .tabItem {
                    Label("Explore", systemImage: "tv")
                }
                
                SearchView(onShowClick: { id in
                    path.append(id)
                })
                .tabItem {
                    Label("Search", systemImage: "magnifyingglass")
                }
                
                LibraryView(onShowClick: { id in
                    path.append(id)
                })
                .tabItem {
                    Label("Library", systemImage: "grid")
                }
                
                SavedView(onShowClick: { id in
                    path.append(id)
                })
                .tabItem {
                    Label("Saved", systemImage: "heart")
                }
                
                AboutView()
                .tabItem {
                    Label("About", systemImage: "info.circle")
                }
            }
            .navigationDestination(for: Int32.self) { id in
                ShowDetailView(showId: id)
            }
        }
        .preferredColorScheme(themeManager.colorScheme)
    }
}
