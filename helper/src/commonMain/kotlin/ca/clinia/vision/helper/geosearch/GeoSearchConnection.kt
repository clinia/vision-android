package ca.clinia.vision.helper.geosearch

import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.geosearch.GeoSearchView
import ca.clinia.vision.core.geosearch.GeoSearchViewModel
import ca.clinia.vision.core.geosearch.connectView
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public fun <R> GeoSearchViewModel.connectSearcher(
    searcher: Searcher<R>,
    geoSearchMode: GeoSearchMode = GeoSearchMode.OnSubmit,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : Connection {
    return GeoSearchConnectionSearcher(this, searcher, geoSearchMode, debouncer)
}

public fun <R, T> GeoSearchConnector<R>.connectView(
    view: GeoSearchView<T>,
    transform: Presenter<R, List<T>>
) : Connection {
    return viewModel.connectView(searcher, view, transform)
}