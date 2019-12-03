package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions

internal data class QuerySuggestionsConnectionView<R, T>(
    private val searcher: SearcherQuerySuggestions<R>,
    private val view: QuerySuggestionsView<T>,
    private val presenter: Presenter<R, List<T>>
) : ConnectionImpl() {

    private val callback: Callback<R?> = { response ->
        if (response != null) {
            view.setSuggestions(presenter(response))
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