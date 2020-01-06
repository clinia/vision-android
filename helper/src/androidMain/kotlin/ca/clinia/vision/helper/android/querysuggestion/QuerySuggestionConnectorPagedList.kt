package ca.clinia.vision.helper.android.querysuggestion

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public data class QuerySuggestionConnectorPagedList<R, RQS>(
    public val searcher: Searcher<R>,
    public val searcherQuerySuggestions: SearcherQuerySuggestions<RQS>,
    public val pagedList: List<LiveData<out PagedList<out Any>>>,
    public val viewModel: QuerySuggestionViewModel = QuerySuggestionViewModel(),
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, searcherQuerySuggestions, pagedList, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}