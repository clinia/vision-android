package ca.clinia.vision.helper.searcher

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal class SearcherQuerySuggestionsExceptionHandler(
    private val searcherQuerySuggestions: SearcherQuerySuggestions
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        searcherQuerySuggestions.error.value = exception
        searcherQuerySuggestions.isLoading.value = false
    }
}