package ca.clinia.vision.helper.searcher

import ca.clinia.search.client.Index
import ca.clinia.search.model.response.ResponseSearch
import ca.clinia.search.model.search.Query
import ca.clinia.search.transport.RequestOptions
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.Sequencer
import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.*

public class SearcherSingleIndex(
    public var index: Index,
    public val query: Query = Query(),
    public val requestOptions: RequestOptions? = null,
    override val coroutineScope: CoroutineScope = SearcherScope()
) : Searcher<ResponseSearch> {

    internal val sequencer = Sequencer()

    override val isLoading = SubscriptionValue(false)
    override val error = SubscriptionValue<Throwable?>(null)
    override val response = SubscriptionValue<ResponseSearch?>(null)

    private val options = requestOptions.withUserAgent()
    private val exceptionHandler = SearcherExceptionHandler(this)

    override fun setQuery(text: String?) {
        this.query.query = text
    }

    override fun searchAsync(): Job {
        return coroutineScope.launch(exceptionHandler) {
            isLoading.value = true
            response.value = withContext(Dispatchers.Default) { search() }
            isLoading.value = false
        }.also {
            sequencer.addOperation(it)
        }
    }

    override suspend fun search(): ResponseSearch {
        return index.search(query, options)
    }

    override fun cancel() {
        sequencer.cancelAll()
    }
}