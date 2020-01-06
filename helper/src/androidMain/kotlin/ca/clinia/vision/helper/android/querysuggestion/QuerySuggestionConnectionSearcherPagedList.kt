package ca.clinia.vision.helper.android.querysuggestion

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions

internal data class QuerySuggestionConnectionSearcherPagedList<R, RQS>(
    private val viewModel: QuerySuggestionViewModel,
    private val searcher: Searcher<R>,
    private val searcherQuerySuggestions: SearcherQuerySuggestions<RQS>,
    private val pagedList: List<LiveData<out PagedList<out Any>>>,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val searchForSuggestions: Callback<String?> = { query ->
        searcherQuerySuggestions.setQuery(query)
        debouncer.debounce(searcherQuerySuggestions) {
            searchQuerySuggestionsAsync()
        }
    }
    private val searchOnSubmit: Callback<String?> = { query ->
        searcher.setQuery(query)
        pagedList.forEach {
            it.value?.dataSource?.invalidate()
        }
    }

    override fun connect() {
        super.connect()
        viewModel.query.subscribe(searchForSuggestions)
        viewModel.eventSubmit.subscribe(searchOnSubmit)
    }

    override fun disconnect() {
        super.disconnect()

        viewModel.query.unsubscribe(searchForSuggestions)
        viewModel.eventSubmit.unsubscribe(searchOnSubmit)
    }
}