package ca.clinia.vision.core.geosearch

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searchbox.SearchBoxConnectionView
import ca.clinia.vision.core.searchbox.SearchBoxView
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searcher.Searcher

public fun <R, T> Searcher<R>.connectGeoSearchView(
    viewModel: GeoSearchViewModel,
    view: GeoSearchView<T>,
    transform: (R) -> List<T>
) : Connection {
    return GeoSearchConnectionView(this, viewModel, view, transform)
}

public fun <R, T> GeoSearchViewModel.connectView(
    searcher: Searcher<R>,
    view: GeoSearchView<T>,
    transform: (R) -> List<T>
) : Connection {
    return GeoSearchConnectionView(searcher, this, view, transform)
}