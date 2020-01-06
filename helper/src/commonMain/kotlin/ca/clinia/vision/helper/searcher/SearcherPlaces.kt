package ca.clinia.vision.helper.searcher

import ca.clinia.search.client.ClientPlaces
import ca.clinia.search.model.places.PlacesQuery
import ca.clinia.search.model.response.ResponseSearchPlaces
import ca.clinia.search.transport.RequestOptions
import ca.clinia.vision.core.searcher.SearcherPlaces
import ca.clinia.vision.core.searcher.Sequencer
import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.*

public class SearcherPlaces(
    public val clientPlaces: ClientPlaces,
    public val query: PlacesQuery = PlacesQuery(),
    public val requestOptions: RequestOptions? = null,
    override val coroutineScope: CoroutineScope = SearcherPlacesScope()
) : SearcherPlaces<ResponseSearchPlaces> {

    internal val sequencer = Sequencer()

    override val isLoading = SubscriptionValue(false)
    override val error = SubscriptionValue<Throwable?>(null)
    override val response = SubscriptionValue<ResponseSearchPlaces?>(null)

    private val options = requestOptions.withUserAgent()
    private val exceptionHandler = SearcherPlacesExceptionHandler(this)

    override fun setQuery(text: String?) {
        this.query.query = text
    }

    override fun searchPlacesAsync(): Job {
        return coroutineScope.launch(exceptionHandler) {
            isLoading.value = true
            response.value = withContext(Dispatchers.Default) { searchPlaces() }
            isLoading.value = false
        }.also {
            sequencer.addOperation(it)
        }
    }

    override suspend fun searchPlaces(): ResponseSearchPlaces {
        return clientPlaces.searchPlaces(query, options)
    }

    override fun cancel() {
        sequencer.cancelAll()
    }
}