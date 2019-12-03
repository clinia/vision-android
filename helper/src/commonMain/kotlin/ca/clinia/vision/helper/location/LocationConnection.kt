package ca.clinia.vision.helper.location

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.location.LocationBoxView
import ca.clinia.vision.core.location.LocationBoxViewModel
import ca.clinia.vision.core.location.connectView
import ca.clinia.vision.core.searcher.*

public fun <R, RP> LocationBoxViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherPlaces: SearcherPlaces<RP>,
    debouncer: Debouncer = Debouncer(debouncePlacesInMillis)
) : Connection {
    return LocationConnectionSearcher(
        this,
        searcher,
        searcherPlaces,
        debouncer
    )
}

public fun <R, RP> LocationConnector<R, RP>.connectView(
    view: LocationBoxView
): Connection {
    return viewModel.connectView(view)
}