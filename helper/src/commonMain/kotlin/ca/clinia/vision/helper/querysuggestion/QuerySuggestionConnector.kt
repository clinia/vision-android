package ca.clinia.vision.helper.querysuggestion

import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.searcher.*

public data class QuerySuggestionConnector<R, RQS>(
    public val searcher: Searcher<R>,
    public val searcherQuerySuggestions: SearcherQuerySuggestions<RQS>,
    public val viewModel: QuerySuggestionViewModel = QuerySuggestionViewModel(),
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, searcherQuerySuggestions, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}