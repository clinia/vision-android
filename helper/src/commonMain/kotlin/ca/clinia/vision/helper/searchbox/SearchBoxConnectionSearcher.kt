package ca.clinia.vision.helper.searchbox

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher

internal data class SearchBoxConnectionSearcher<R>(
    private val viewModel: SearchBoxViewModel,
    private val searcher: Searcher<R>,
    private val searchMode: SearchMode,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val searchAsYouType: Callback<String?> = { query ->
        searcher.setQuery(query)
        debouncer.debounce(searcher) { searchAsync() }
    }
    private val searchOnSubmit: Callback<String?> = { query ->
        searcher.setQuery(query)
        searcher.searchAsync()
    }

    override fun connect() {
        super.connect()
        when (searchMode) {
            SearchMode.AsYouType -> viewModel.query.subscribe(searchAsYouType)
            SearchMode.OnSubmit -> viewModel.eventSubmit.subscribe(searchOnSubmit)
        }
    }

    override fun disconnect() {
        super.disconnect()
        when (searchMode) {
            SearchMode.AsYouType -> viewModel.query.unsubscribe(searchAsYouType)
            SearchMode.OnSubmit -> viewModel.eventSubmit.unsubscribe(searchOnSubmit)
        }
    }
}