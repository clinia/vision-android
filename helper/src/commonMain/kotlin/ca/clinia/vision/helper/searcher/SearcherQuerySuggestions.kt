package ca.clinia.vision.helper.searcher

import ca.clinia.search.client.ClientSearch
import ca.clinia.search.model.response.ResponseQuerySuggestions
import ca.clinia.search.model.suggest.SuggestionQuery
import ca.clinia.search.transport.RequestOptions
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions
import ca.clinia.vision.core.searcher.Sequencer
import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.*

public class SearcherQuerySuggestions(
    public val clientSearch: ClientSearch,
    public val query: SuggestionQuery = SuggestionQuery(),
    public val requestOptions: RequestOptions? = null,
    override val coroutineScope: CoroutineScope = SearcherQuerySuggestionsScope()
) : SearcherQuerySuggestions<ResponseQuerySuggestions> {

    internal val sequencer = Sequencer()

    override val isLoading = SubscriptionValue(false)
    override val error = SubscriptionValue<Throwable?>(null)
    override val response = SubscriptionValue<ResponseQuerySuggestions?>(null)

    private val options = requestOptions.withUserAgent()
    private val exceptionHandler = SearcherQuerySuggestionsExceptionHandler(this)

    override fun setQuery(text: String?) {
        this.query.query = text
    }

    override fun searchQuerySuggestionsAsync(): Job {
        return coroutineScope.launch(exceptionHandler) {
            isLoading.value = true
            response.value = withContext(Dispatchers.Default) { searchQuerySuggestions() }
            isLoading.value = false
        }.also {
            sequencer.addOperation(it)
        }
    }

    override suspend fun searchQuerySuggestions(): ResponseQuerySuggestions {
        return clientSearch.suggest(query, options)
    }

    override fun cancel() {
        sequencer.cancelAll()
    }
}