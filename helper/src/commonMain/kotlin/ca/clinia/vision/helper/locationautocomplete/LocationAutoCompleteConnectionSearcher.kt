package ca.clinia.vision.helper.locationautocomplete

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherPlaces

internal data class LocationAutoCompleteConnectionSearcher<R, RP>(
    private val viewModel: LocationAutoCompleteViewModel,
    private val searcher: Searcher<R>,
    private val searcherPlaces: SearcherPlaces<RP>,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val searchForPlaces: Callback<String?> = { query ->
        searcherPlaces.setQuery(query)
        debouncer.debounce(searcherPlaces) { searchPlacesAsync() }
    }

    private val searchOnSubmit: Callback<String?> = { location ->
        searcher.setLocation(location)
        searcher.searchAsync()
    }

    override fun connect() {
        super.connect()

        // Suggestions
        viewModel.query.subscribe(searchForPlaces)

        // Search
        viewModel.location.subscribe(searchOnSubmit)
        viewModel.eventSubmit.subscribe(searchOnSubmit)
    }

    override fun disconnect() {
        super.disconnect()
        viewModel.query.unsubscribe(searchForPlaces)
        viewModel.location.unsubscribe(searchOnSubmit)
        viewModel.eventSubmit.unsubscribe(searchOnSubmit)
    }
}