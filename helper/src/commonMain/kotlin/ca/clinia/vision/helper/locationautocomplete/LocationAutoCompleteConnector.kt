package ca.clinia.vision.helper.locationautocomplete

import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherPlaces
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public data class LocationAutoCompleteConnector<R, RP>(
    public val searcher: Searcher<R>,
    public val searcherPlaces: SearcherPlaces<RP>,
    public val viewModel: LocationAutoCompleteViewModel = LocationAutoCompleteViewModel(),
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, searcherPlaces, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}