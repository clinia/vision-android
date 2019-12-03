package ca.clinia.vision.core.locationautocomplete

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searcher.SearcherPlaces

internal data class LocationAutoCompleteSuggestionsConnectionView<R, T>(
    private val searcher: SearcherPlaces<R>,
    private val view: LocationAutoCompleteSuggestionsView<T>,
    private val presenter: Presenter<R, List<T>>
) : ConnectionImpl() {

    private val callback: Callback<R?> = {response ->
        if (response != null) {
            view.setPlaces(presenter(response))
        }
    }

    override fun connect() {
        super.connect()

        searcher.response.subscribe(callback)
    }

    override fun disconnect() {
        super.disconnect()
        searcher.response.unsubscribe(callback)
    }
}