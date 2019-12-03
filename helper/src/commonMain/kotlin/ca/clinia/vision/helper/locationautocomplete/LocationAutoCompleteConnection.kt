package ca.clinia.vision.helper.locationautocomplete

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteView
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteViewModel
import ca.clinia.vision.core.locationautocomplete.connectView
import ca.clinia.vision.core.searcher.*

public fun <R, RP> LocationAutoCompleteViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherPlaces: SearcherPlaces<RP>,
    debouncer: Debouncer = Debouncer(debouncePlacesInMillis)
) : Connection {
    return LocationAutoCompleteConnectionSearcher(this, searcher, searcherPlaces, debouncer)
}

public fun <R, RP> LocationAutoCompleteConnector<R, RP>.connectView(
    view: LocationAutoCompleteView
): Connection {
    return viewModel.connectView(view)
}